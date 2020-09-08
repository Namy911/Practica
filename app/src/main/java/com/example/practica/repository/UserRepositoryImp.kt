package com.example.practica.repository

import com.example.practica.data.entity.User
import com.example.practica.repository.contract.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class UserRepositoryImp @Inject constructor(private val store: User.Store):
    UserRepository {
    override suspend fun save(user: User) {
        store.save(user)
    }

    override fun loadAll(): Flow<List<User>> =
        store.loadDistinctUnitChange()
}