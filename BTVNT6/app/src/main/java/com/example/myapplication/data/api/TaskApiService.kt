package com.example.myapplication.data.api

import com.example.myapplication.data.model.Task
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskApiService {
    @GET("tasks")
    suspend fun getAllTasks(): Response<List<Task>>
    
    @GET("tasks")
    suspend fun getAllTasksRaw(): Response<ResponseBody>
    
    @GET("task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): Response<Task>
    
    @GET("task/{id}")
    suspend fun getTaskByIdRaw(@Path("id") id: Int): Response<ResponseBody>
    
    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}
