package com.example.chatstreams.di

import android.content.Context
import com.example.chatstreams.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.notifications.handler.NotificationConfig
import io.getstream.chat.android.pushprovider.firebase.FirebasePushDeviceGenerator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    /*
    * There is another way to pass the keys like this using local properties and build method
    * but for the brevity purposes here string resources is used*/
    fun provideChatClient(@ApplicationContext context: Context) =
        ChatClient.Builder(context.getString(R.string.api_key), context)
            .notifications(notificationConfig)
            .build()

    private val notificationConfig = NotificationConfig(
        pushDeviceGenerators = listOf(FirebasePushDeviceGenerator())
    )


}