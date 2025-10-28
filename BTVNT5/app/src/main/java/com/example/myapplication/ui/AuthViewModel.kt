package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = ""
)

sealed class AuthState {
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
    object SignedOut : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    init {
        checkAuthState()
    }
    
    private fun checkAuthState() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _authState.value = AuthState.Success(
                User(
                    uid = currentUser.uid,
                    name = currentUser.displayName ?: "",
                    email = currentUser.email ?: "",
                    photoUrl = currentUser.photoUrl?.toString() ?: ""
                )
            )
        } else {
            _authState.value = AuthState.SignedOut
        }
    }
    
    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                println("DEBUG: Starting Google Sign-In with token: $idToken")
                _authState.value = AuthState.Loading
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val result = auth.signInWithCredential(credential).await()
                
                println("DEBUG: Firebase auth result: ${result.user?.uid}")
                result.user?.let { user ->
                    _authState.value = AuthState.Success(
                        User(
                            uid = user.uid,
                            name = user.displayName ?: "",
                            email = user.email ?: "",
                            photoUrl = user.photoUrl?.toString() ?: ""
                        )
                    )
                    println("DEBUG: User signed in successfully: ${user.email}")
                }
            } catch (e: Exception) {
                println("DEBUG: Firebase auth error: ${e.message}")
                _authState.value = AuthState.Error(e.message ?: "Đăng nhập thất bại")
            }
        }
    }
    
    fun signOut() {
        viewModelScope.launch {
            try {
                auth.signOut()
                _authState.value = AuthState.SignedOut
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Đăng xuất thất bại")
            }
        }
    }
}
