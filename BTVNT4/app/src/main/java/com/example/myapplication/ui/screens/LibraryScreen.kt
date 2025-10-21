package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Book(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
)

data class Student(
    val id: Int,
    val name: String,
    val borrowedBooks: List<Book> = emptyList()
)

@Composable
fun LibraryScreen(navController: NavHostController) {
    var currentStudentId by remember { mutableStateOf(1) }
    var newBookName by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    var students by remember {
        mutableStateOf(
            listOf(
                Student(1, "Nguyen Van A", listOf(
                    Book(1, "Sách 01", true),
                    Book(2, "Sách 02", true)
                )),
                Student(2, "Nguyen Thi B", listOf(
                    Book(1, "Sách 01", true)
                )),
                Student(3, "Nguyen Van C", emptyList())
            )
        )
    }

    val currentStudent = students.find { it.id == currentStudentId } ?: students[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Hệ thống Quản lý Thư viện",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        when (selectedTab) {
            0 -> {
                // Management Tab
                ManagementContent(
                    student = currentStudent,
                    onStudentChange = { currentStudentId = it },
                    students = students,
                    newBookName = newBookName,
                    onNewBookNameChange = { newBookName = it },
                    onAddBook = {
                        if (newBookName.isNotEmpty()) {
                            // Tạo ID mới cho sách
                            val maxId = students.flatMap { it.borrowedBooks }.maxOfOrNull { it.id } ?: 0
                            val newBook = Book(maxId + 1, newBookName, true)
                            
                            // Cập nhật danh sách sinh viên với sách mới
                            students = students.map { student ->
                                if (student.id == currentStudentId) {
                                    student.copy(borrowedBooks = student.borrowedBooks + newBook)
                                } else student
                            }
                            newBookName = ""
                        }
                    }
                )
            }
            1 -> {
                // Book List Tab
                BookListContent(books = currentStudent.borrowedBooks)
            }
            2 -> {
                // Student Tab
                StudentContent(student = currentStudent)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Navigation
        BottomNavigation(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
    }
}

@Composable
fun ManagementContent(
    student: Student,
    onStudentChange: (Int) -> Unit,
    students: List<Student>,
    newBookName: String,
    onNewBookNameChange: (String) -> Unit,
    onAddBook: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Student Section
        Text(
            text = "Sinh viên",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = student.name,
                onValueChange = { /* Read only for now */ },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2), // Blue 700
                    focusedLabelColor = Color(0xFF1976D2), // Blue 700
                    unfocusedBorderColor = Color(0xFFE0E0E0), // Light Gray 300
                    unfocusedContainerColor = Color(0xFFF5F5F5) // Very Light Gray
                )
            )
            Button(
                onClick = { 
                    val nextStudentId = if (student.id < 3) student.id + 1 else 1
                    onStudentChange(nextStudentId)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2) // Blue 700
                )
            ) {
                Text("Thay đổi", color = Color.White)
            }
        }

        // Book List Section
        Text(
            text = "Danh sách sách",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        // Book List Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    color = Color(0xFFE0E0E0), // Light Gray 300
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            if (student.borrowedBooks.isEmpty()) {
                // Empty state
                Text(
                    text = "Bạn chưa mượn quyển sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // Book list
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(student.borrowedBooks) { book ->
                        BookItem(book = book)
                    }
                }
            }
        }

        // Add Book Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newBookName,
                onValueChange = onNewBookNameChange,
                label = { Text("Tên sách mới") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2), // Blue 700
                    focusedLabelColor = Color(0xFF1976D2), // Blue 700
                    unfocusedBorderColor = Color(0xFFE0E0E0), // Light Gray 300
                    unfocusedContainerColor = Color(0xFFF5F5F5) // Very Light Gray
                )
            )
            Button(
                onClick = onAddBook,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2) // Blue 700
                )
            ) {
                Text("Thêm", color = Color.White)
            }
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = "Select",
            tint = Color.Red,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = book.name,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BookListContent(books: List<Book>) {
    Column {
        Text(
            text = "Danh sách tất cả sách",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        color = Color(0xFFE0E0E0), // Light Gray 300
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Chưa có sách nào được mượn",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(books) { book ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF5F5F5) // Very Light Gray
                        )
                    ) {
                        Text(
                            text = book.name,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StudentContent(student: Student) {
    Column {
        Text(
            text = "Thông tin sinh viên",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F5F5) // Very Light Gray
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Tên sinh viên: ${student.name}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Số sách đã mượn: ${student.borrowedBooks.size}",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun BottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Quản lý",
                isSelected = selectedTab == 0,
                onClick = { onTabSelected(0) }
            )
            BottomNavItem(
                icon = Icons.Default.List,
                label = "DS Sách",
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )
            BottomNavItem(
                icon = Icons.Default.Person,
                label = "Sinh viên",
                isSelected = selectedTab == 2,
                onClick = { onTabSelected(2) }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFF1976D2) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFF1976D2) else Color.Gray
        )
    }
}
