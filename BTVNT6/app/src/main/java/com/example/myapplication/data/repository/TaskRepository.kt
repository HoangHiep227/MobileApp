package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.api.RetrofitClient
import com.example.myapplication.data.model.Task
import com.example.myapplication.data.model.TasksResponse
import com.google.gson.Gson

class TaskRepository {
    private val apiService = RetrofitClient.taskApiService
    private val TAG = "TaskRepository"
    private val gson = Gson()

    suspend fun getAllTasks(): Result<List<Task>> {
        return try {
            // Use ResponseBody to get raw string first
            val response = apiService.getAllTasksRaw()
            Log.d(TAG, "Response code: ${response.code()}")
            Log.d(TAG, "Response isSuccessful: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                val rawBody = response.body()?.string()
                Log.d(TAG, "Raw response body: $rawBody")
                
                if (rawBody != null) {
                    // Try to parse as array first
                    try {
                        val tasks: List<Task> = gson.fromJson(rawBody, Array<Task>::class.java).toList()
                        Log.d(TAG, "Parsed as array, tasks count: ${tasks.size}")
                        return Result.success(tasks)
                    } catch (e: Exception) {
                        Log.d(TAG, "Failed to parse as array, trying as wrapper object: ${e.message}")
                        // Try to parse as wrapper object
                        try {
                            val wrapper: TasksResponse = gson.fromJson(rawBody, TasksResponse::class.java)
                            val tasks = wrapper.extractTasks()
                            Log.d(TAG, "Parsed as wrapper object, tasks count: ${tasks.size}")
                            return Result.success(tasks)
                        } catch (e2: Exception) {
                            Log.e(TAG, "Failed to parse as wrapper object: ${e2.message}", e2)
                            return Result.failure(Exception("Failed to parse response: ${e2.message}"))
                        }
                    }
                } else {
                    Log.e(TAG, "Response body is null")
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Failed to fetch tasks: ${response.code()}, errorBody: $errorBody")
                Result.failure(Exception("Failed to fetch tasks: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getAllTasks: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun getTaskById(id: Int): Result<Task> {
        return try {
            // Use ResponseBody to get raw string first
            val response = apiService.getTaskByIdRaw(id)
            Log.d(TAG, "Response code for task $id: ${response.code()}")
            Log.d(TAG, "Response isSuccessful: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                val rawBody = response.body()?.string()
                Log.d(TAG, "Raw response body for task $id: $rawBody")
                
                if (rawBody != null) {
                    // Try to parse as wrapper object first (since API returns wrapper)
                    try {
                        val taskJson = gson.fromJson(rawBody, com.google.gson.JsonObject::class.java)
                        var task: Task? = null
                        
                        // Try "data" field first (this is what the API uses)
                        if (taskJson.has("data")) {
                            task = gson.fromJson(taskJson.get("data"), Task::class.java)
                            Log.d(TAG, "Parsed from wrapper.data field")
                        }
                        // Try "task" field
                        else if (taskJson.has("task")) {
                            task = gson.fromJson(taskJson.get("task"), Task::class.java)
                            Log.d(TAG, "Parsed from wrapper.task field")
                        }
                        // Try "result" field
                        else if (taskJson.has("result")) {
                            task = gson.fromJson(taskJson.get("result"), Task::class.java)
                            Log.d(TAG, "Parsed from wrapper.result field")
                        }
                        
                        if (task != null) {
                            // Verify that we got actual data (not all nulls)
                            if (task.title != null || task.id > 0) {
                                Log.d(TAG, "Task detail loaded - Title: ${task.title}, Description: ${task.description}, Date: ${task.date}, Category: ${task.category}, Status: ${task.status}, Priority: ${task.priority}")
                                Log.d(TAG, "Subtasks count: ${task.subtasks?.size ?: 0}, Attachments count: ${task.attachments?.size ?: 0}")
                                return Result.success(task)
                            } else {
                                Log.e(TAG, "Task parsed but all fields are null - trying direct parse")
                            }
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "Failed to parse as wrapper object, trying direct parse: ${e.message}")
                    }
                    
                    // Fallback: Try to parse as direct Task object
                    try {
                        val task: Task = gson.fromJson(rawBody, Task::class.java)
                        // Verify that we got actual data
                        if (task.title != null || task.id > 0) {
                            Log.d(TAG, "Parsed as direct Task object - Title: ${task.title}, Description: ${task.description}, Date: ${task.date}, Category: ${task.category}, Status: ${task.status}, Priority: ${task.priority}")
                            Log.d(TAG, "Subtasks count: ${task.subtasks?.size ?: 0}, Attachments count: ${task.attachments?.size ?: 0}")
                            return Result.success(task)
                        } else {
                            Log.e(TAG, "Direct parse succeeded but all fields are null")
                            return Result.failure(Exception("Parsed task but all fields are null - check JSON structure"))
                        }
                    } catch (e2: Exception) {
                        Log.e(TAG, "Failed to parse as direct Task object: ${e2.message}", e2)
                        return Result.failure(Exception("Failed to parse response: ${e2.message}"))
                    }
                } else {
                    Log.e(TAG, "Response body is null for task $id")
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Failed to fetch task $id: ${response.code()}, errorBody: $errorBody")
                Result.failure(Exception("Failed to fetch task: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in getTaskById: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun deleteTask(id: Int): Result<Unit> {
        return try {
            val response = apiService.deleteTask(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "Failed to delete task $id: ${response.code()}, errorBody: $errorBody")
                Result.failure(Exception("Failed to delete task: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception in deleteTask: ${e.message}", e)
            Result.failure(e)
        }
    }
}
