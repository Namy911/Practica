package com.example.practica.data

sealed class TaskSchema {
    companion object{
        const val DB_NAME: String = "task.db"
    }
    class UserTable{
        companion object{
            const val TABLE_NAME = "users"
            const val ROW_NAME = "name"
            const val ROW_AGE = "age"
            const val ROW_ID = "_id"
        }
    }
}