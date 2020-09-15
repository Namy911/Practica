package com.example.practica.di

import com.example.practica.repository.UserRepositoryImp
import com.example.practica.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
abstract class UserRepositoryModule{
    @Singleton
    @Binds
    abstract fun provideUserRepository(store: UserRepositoryImp): UserRepository

}