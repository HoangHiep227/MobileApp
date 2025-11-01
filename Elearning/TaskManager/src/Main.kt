// Interface là một dạng ABSTRACT — dùng để định nghĩa hành vi chung mà các lớp có thể triển khai thể hiện tính chất trừu tượng
interface Manageable {
    fun displayInfo()   // Hành vi trừu tượng: hiển thị thông tin (Abstraction)
    fun markDone()      // Hành vi trừu tượng: đánh dấu hoàn thành (Abstraction)
}


enum class TaskStatus(val description: String) {
    NOT_DONE("Not done"),
    DONE("Done")
}


// Lớp Task thể hiện mô hình đối tượng: có thuộc tính và hành vi (Kế thừa)
open class Task(
    private val title: String,          // Đóng gói — thuộc tính private
    private val description: String,    // chỉ được truy cập qua getter
    private var status: TaskStatus = TaskStatus.NOT_DONE
) : Manageable {                        // Kế thừa interface Manageable

    // Ghi đè phương thức trong interface (Đa hình)
    override fun displayInfo() {
        println(" $title - $description | Status: ${status.description}")
    }

    override fun markDone() {
        status = TaskStatus.DONE
        println("Task \"$title\" marked as done!")
    }

    // Cung cấp getter (Đóng gói)
    fun getTitle() = title
    fun getStatus() = status
}

// Lớp UrgentTask kế thừa Task (Kế thừa)
class UrgentTask(
    title: String,
    description: String,
    private val priorityLevel: Int
) : Task(title, description) {

    // Ghi đè (override) phương thức của lớp cha — thể hiện tính đa hình
    override fun displayInfo() {
        println("URGENT [P$priorityLevel]: ${getTitle()} | Status: ${getStatus().description}")
    }
}


class TaskManager {
    private val tasks = mutableListOf<Manageable>()

    fun addTask(task: Manageable) {
        tasks.add(task)
        println("Task added successfully.")
    }

    fun listTasks() {
        if (tasks.isEmpty()) {
            println("No tasks found.")
            return
        }
        println("\n Task List:")
        tasks.forEachIndexed { index, task ->
            print("${index + 1}. ")
            task.displayInfo()   // Gọi hàm ghi đè ( tính đa hình) dựa vào hàm ghi đè mà goị đến hàm có logic tương ứng
        }
    }

    fun markTaskDone(index: Int) {
        if (index in 1..tasks.size) {
            tasks[index - 1].markDone()
        } else {
            println("Invalid task number.")
        }
    }

    fun removeTask(index: Int) {
        if (index in 1..tasks.size) {
            println("Task removed.")
            tasks.removeAt(index - 1)
        } else {
            println("Invalid task number.")
        }
    }

    // sử dụng Set để lọc ra các tiêu đề công việc duy nhất
    fun uniqueTitles(): Set<String> {
        val titles = mutableSetOf<String>()
        for (task in tasks) {
            if (task is Task) {
                titles.add(task.getTitle())
            }
        }
        return titles
    }
}

fun main() {
    val manager = TaskManager()
    var running = true

    while (running) {
        println(
            """
            
            === Task Manager (OOP Version) ===
            1. Add Normal Task
            2. Add Urgent Task
            3. List Tasks
            4. Mark Task Done
            5. Remove Task
            6. Show Unique Titles
            7. Exit
            """.trimIndent()
        )

        print("Choose an option: ")
        when (readlnOrNull()?.trim()) {
            "1" -> {
                val title = readNonEmptyInput("Enter title: ")
                val desc = readNonEmptyInput("Enter description: ")
                manager.addTask(Task(title, desc))
            }

            "2" -> {
                val title = readNonEmptyInput("Enter title: ")
                val desc = readNonEmptyInput("Enter description: ")
                val level = readIntInput("Enter priority level (1–5): ", 1, 5)
                manager.addTask(UrgentTask(title, desc, level))
            }

            "3" -> manager.listTasks()

            "4" -> {
                val index = readIntInput("Enter task number to mark as done: ", 1, managerSize(manager))
                manager.markTaskDone(index)
            }

            "5" -> {
                val index = readIntInput("Enter task number to remove: ", 1, managerSize(manager))
                manager.removeTask(index)
            }

            "6" -> {
                println("Unique Task Titles:")
                manager.uniqueTitles().forEach { println("- $it") } // Gọi hàm có sử dụng Set
            }

            "7" -> {
                println("Goodbye!")
                running = false
            }

            else -> println("Invalid option, please try again.")
        }
    }
}


fun readNonEmptyInput(prompt: String): String {
    while (true) {
        print(prompt)
        val input = readlnOrNull()?.trim()
        if (!input.isNullOrEmpty()) return input
        println("Input cannot be empty. Please try again.")
    }
}

fun readIntInput(prompt: String, min: Int, max: Int): Int {
    while (true) {
        print(prompt)
        val input = readlnOrNull()?.toIntOrNull()
        if (input != null && input in min..max) return input
        println("Please enter a valid number between $min and $max.")
    }
}

fun managerSize(manager: TaskManager): Int {
    val field = TaskManager::class.java.getDeclaredField("tasks")
    field.isAccessible = true
    val list = field.get(manager) as List<*>
    return list.size.coerceAtLeast(1)
}
