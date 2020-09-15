package com.example.practica.repository

import com.example.practica.data.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun save(user: User)
    suspend fun update(user: User)
    suspend fun delete(user: User)
    fun loadAll(): Flow<List<User>>
    fun getUser(id: Int): Flow<User>
}