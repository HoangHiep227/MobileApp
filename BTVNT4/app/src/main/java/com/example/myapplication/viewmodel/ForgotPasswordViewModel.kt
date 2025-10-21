package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ForgotPasswordViewModel : ViewModel() {
    var email by mutableStateOf("")
    var code by mutableStateOf("")
    var password by mutableStateOf("")

	
	var shouldShowSummaryDialog by mutableStateOf(false)

	fun resetAll() {
		email = ""
		code = ""
		password = ""
		shouldShowSummaryDialog = false
	}
}
