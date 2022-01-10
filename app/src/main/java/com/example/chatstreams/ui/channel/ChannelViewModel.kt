package com.example.chatstreams.ui.channel

import androidx.lifecycle.ViewModel
import com.example.chatstreams.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.models.User
import javax.inject.Inject

@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun logOut() {
        userRepository.logOutUser()
    }

    fun getUser(): User? {
        return userRepository.getCurrentUser()
    }
}