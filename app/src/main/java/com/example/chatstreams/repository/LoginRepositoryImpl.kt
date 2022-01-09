package com.example.chatstreams.repository

import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.Call
import io.getstream.chat.android.client.models.ConnectionData
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val client: ChatClient) : LoginRepository {
    override fun loginUser(userId: String, userName: String): Call<ConnectionData> {
        return client.connectGuestUser(userId, userName)
    }
}