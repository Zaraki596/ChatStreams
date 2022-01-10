package com.example.chatstreams.di

import com.example.chatstreams.repository.UserRepository
import com.example.chatstreams.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindUserRepository(loginRepositoryImpl: UserRepositoryImpl): UserRepository
}