package com.example.chatstreams.repository

import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.Call
import io.getstream.chat.android.client.models.ConnectionData
import io.getstream.chat.android.client.models.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val client: ChatClient) : UserRepository {
    override fun loginUser(userId: String, userName: String): Call<ConnectionData> {
        return client.connectGuestUser(userId, userName)
    }

    override fun logOutUser() {
       client.disconnect()
    }

    override fun getCurrentUser(): User? {
        return client.getCurrentUser()
    }
}