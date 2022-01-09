package com.example.chatstreams.repository

import io.getstream.chat.android.client.call.Call
import io.getstream.chat.android.client.models.ConnectionData

interface LoginRepository {
    fun loginUser(userId: String, userName: String) : Call<ConnectionData>
}