package com.example.chatstreams.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatstreams.repository.LoginRepository
import com.example.chatstreams.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.call.await
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    /*
    * We are using SharedFlow here instead of StateFlow since the loginEvent is one time event
    * */
    private val _loginEvent = MutableSharedFlow<LogInEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    private fun isValidUsername(username: String) = username.length >= Constants.MIN_USERNAME_LENGTH

    fun connectUser(username: String) {
        val trimmedUsername = username.trim()
        viewModelScope.launch {
            if (isValidUsername(trimmedUsername)) {
                val result = loginRepository.loginUser(
                    userId = trimmedUsername,
                    userName = trimmedUsername
                ).await()
                if (result.isError) {
                    _loginEvent.emit(
                        LogInEvent.ErrorLogin(result.error().message ?: "Unknown error")
                    )
                    return@launch
                }
                _loginEvent.emit(LogInEvent.Success)
            } else{
                _loginEvent.emit(LogInEvent.ErrorInputTooShort)
            }
        }
    }

    sealed class LogInEvent {
        object ErrorInputTooShort : LogInEvent()
        data class ErrorLogin(val errorMessage: String) : LogInEvent()
        object Success : LogInEvent()
    }
}