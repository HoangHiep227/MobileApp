package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

// Wrapper class for API response
// API có thể trả về: { "data": [...] } hoặc { "tasks": [...] } hoặc { "results": [...] }
data class TasksResponse(
    @SerializedName("data")
    val data: List<Task>? = null,
    
    @SerializedName("tasks")
    val tasks: List<Task>? = null,
    
    @SerializedName("results")
    val results: List<Task>? = null
) {
    fun extractTasks(): List<Task> {
        return data ?: tasks ?: results ?: emptyList()
    }
}
