package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class Task(
    val id: Int,
    val title: String? = null,
    val description: String? = null,
    val category: String? = null,
    val status: String? = null,
    val priority: String? = null,
    @SerializedName("dueDate")
    val date: String? = null,
    val subtasks: List<Subtask>? = emptyList(),
    val attachments: List<Attachment>? = emptyList()
)

data class Subtask(
    val id: Int,
    val title: String? = null,
    @SerializedName("isCompleted")
    val completed: Boolean = false
)

data class Attachment(
    val id: Int,
    @SerializedName("fileName")
    val name: String? = null,
    @SerializedName("fileUrl")
    val url: String? = null
)
