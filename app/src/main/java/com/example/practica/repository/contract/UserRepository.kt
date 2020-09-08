package com.example.practica.repository.contract

import com.example.practica.data.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun save(user: User)
    fun loadAll(): Flow<List<User>>
}