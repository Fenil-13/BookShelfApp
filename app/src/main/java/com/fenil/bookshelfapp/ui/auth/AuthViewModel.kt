package com.fenil.bookshelfapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenil.bookshelfapp.domain.model.User
import com.fenil.bookshelfapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _signUpResponse = MutableLiveData<Boolean?>()
    val signUpResponse: LiveData<Boolean?> = _signUpResponse

    private val _loginResponse = MutableLiveData<User?>()
    val loginResponse: LiveData<User?> = _loginResponse

    private val _loggedInUserResponse = MutableLiveData<User?>()
    val loggedInUserResponse: LiveData<User?> = _loggedInUserResponse

    fun signUp(user: User) {
        viewModelScope.launch {
            val response = userRepository.signUp(user)
            _signUpResponse.postValue(response)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = userRepository.login(email, password)
            _loginResponse.postValue(user)
        }
    }

    fun logout(email: String) {
        viewModelScope.launch {
            userRepository.logout(email)
        }
    }

    fun getLoggedInUser(){
        viewModelScope.launch {
            val response = userRepository.getLoggedInUser()
            _loggedInUserResponse.postValue(response)
        }
    }
}
