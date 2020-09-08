package com.example.practica.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practica.data.entity.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class TaskDataBase: RoomDatabase() {
    abstract fun userStore(): User.Store
}