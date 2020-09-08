package com.example.practica.data.entity

import androidx.room.*
import com.example.practica.data.TaskSchema
import com.example.practica.data.TaskSchema.UserTable.Companion.ROW_AGE
import com.example.practica.data.TaskSchema.UserTable.Companion.ROW_ID
import com.example.practica.data.TaskSchema.UserTable.Companion.ROW_NAME
import com.example.practica.data.TaskSchema.UserTable.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Entity(tableName = TABLE_NAME)
data class User(
    @ColumnInfo(name = ROW_NAME)
    val name: String,

    @ColumnInfo(name = ROW_AGE)
    val age: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ROW_ID)
    val id: Int = 0
) {
    @Dao
    interface Store{

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun save(user: User)

        @Query("SELECT * FROM $TABLE_NAME")
        fun loadAll(): Flow<List<User>>

        fun loadDistinctUnitChange() =
            loadAll().distinctUntilChanged()
    }
}