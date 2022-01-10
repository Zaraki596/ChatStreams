package com.example.chatstreams.repository

import io.getstream.chat.android.client.call.Call
import io.getstream.chat.android.client.models.ConnectionData
import io.getstream.chat.android.client.models.User

interface UserRepository {
    fun loginUser(userId: String, userName: String) : Call<ConnectionData>
    fun logOutUser()
    fun getCurrentUser() : User?
}