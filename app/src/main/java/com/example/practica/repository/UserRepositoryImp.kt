package com.example.practica.repository

import com.example.practica.data.entity.User
import javax.inject.Inject



class UserRepositoryImp @Inject constructor(private val store: User.Store): UserRepository {
    override suspend fun save(user: User) {
        store.save(user)
    }

    override suspend fun update(user: User) {
        store.update(user)
    }

    override suspend fun delete(user: User) {
        store.delete(user)
    }

    override fun loadAll()=
        store.loadDistinctUnitChange()

    override fun getUser(id: Int)=
        store.getUserDistinctUnitChange(id)

}