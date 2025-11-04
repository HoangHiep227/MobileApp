package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Task
import com.example.myapplication.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TaskListUiState {
    data object Loading : TaskListUiState()
    data class Success(val tasks: List<Task>) : TaskListUiState()
    data class Error(val message: String) : TaskListUiState()
}

sealed class TaskDetailUiState {
    data object Loading : TaskDetailUiState()
    data class Success(val task: Task) : TaskDetailUiState()
    data class Error(val message: String) : TaskDetailUiState()
}

class TaskViewModel(private val repository: TaskRepository = TaskRepository()) : ViewModel() {
    private val _taskListState = MutableStateFlow<TaskListUiState>(TaskListUiState.Loading)
    val taskListState: StateFlow<TaskListUiState> = _taskListState.asStateFlow()

    private val _taskDetailState = MutableStateFlow<TaskDetailUiState>(TaskDetailUiState.Loading)
    val taskDetailState: StateFlow<TaskDetailUiState> = _taskDetailState.asStateFlow()

    private val TAG = "TaskViewModel"

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _taskListState.value = TaskListUiState.Loading
            Log.d(TAG, "Loading tasks...")
            
            repository.getAllTasks()
                .onSuccess { tasks ->
                    Log.d(TAG, "Tasks loaded successfully: ${tasks.size} tasks")
                    _taskListState.value = TaskListUiState.Success(tasks)
                }
                .onFailure { exception ->
                    val errorMessage = exception.message ?: "Unknown error"
                    Log.e(TAG, "Failed to load tasks: $errorMessage", exception)
                    _taskListState.value = TaskListUiState.Error("Error: $errorMessage")
                }
        }
    }

    fun loadTaskDetail(taskId: Int) {
        viewModelScope.launch {
            _taskDetailState.value = TaskDetailUiState.Loading
            Log.d(TAG, "Loading task detail for ID: $taskId")
            
            repository.getTaskById(taskId)
                .onSuccess { task ->
                    Log.d(TAG, "Task detail loaded successfully: ${task.title}")
                    _taskDetailState.value = TaskDetailUiState.Success(task)
                }
                .onFailure { exception ->
                    val errorMessage = exception.message ?: "Unknown error"
                    Log.e(TAG, "Failed to load task detail: $errorMessage", exception)
                    _taskDetailState.value = TaskDetailUiState.Error("Error: $errorMessage")
                }
        }
    }

    fun deleteTask(taskId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
                .onSuccess {
                    Log.d(TAG, "Task deleted successfully: $taskId")
                    onSuccess()
                    loadTasks()
                }
                .onFailure { exception ->
                    val errorMessage = exception.message ?: "Failed to delete"
                    Log.e(TAG, "Failed to delete task: $errorMessage", exception)
                    _taskDetailState.value = TaskDetailUiState.Error("Error: $errorMessage")
                }
        }
    }
}
