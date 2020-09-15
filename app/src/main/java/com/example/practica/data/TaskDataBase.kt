package com.example.practica.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practica.data.TaskSchema.UserTable.Companion.ROW_AGE
import com.example.practica.data.TaskSchema.UserTable.Companion.ROW_NAME
import com.example.practica.data.TaskSchema.UserTable.Companion.TABLE_NAME
import com.example.practica.data.entity.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class TaskDataBase: RoomDatabase() {
    abstract fun userStore(): User.Store
}
class PrepopulateDataBase: RoomDatabase.Callback(){
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL("""
            INSERT INTO $TABLE_NAME($ROW_NAME, $ROW_AGE) VALUES ("test", 1), ("test2", 1), ("test3", 1);
        """.trimIndent())
    }
}