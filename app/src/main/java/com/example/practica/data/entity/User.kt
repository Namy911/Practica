package com.example.practica.data.entity

import android.os.Parcelable
import androidx.room.*
import com.example.practica.data.TaskSchema.UserTable.Companion.ROW_AGE
import com.example.practica.data.TaskSchema.UserTable.Companion.ROW_ID
import com.example.practica.data.TaskSchema.UserTable.Companion.ROW_NAME
import com.example.practica.data.TaskSchema.UserTable.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Entity(tableName = TABLE_NAME)
@Parcelize
data class User(
    @ColumnInfo(name = ROW_NAME)
    var name: String,

    @ColumnInfo(name = ROW_AGE)
    var age: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ROW_ID)
    var id: Int = 0
): Parcelable {
    @Dao
    interface Store{

        @Insert
        suspend fun save(user: User)

        @Update(onConflict = OnConflictStrategy.REPLACE)
        suspend fun update(user: User)

        @Delete
        suspend fun delete(user: User)

        @Query("SELECT * FROM $TABLE_NAME")
        fun loadAll(): Flow<List<User>>

        fun loadDistinctUnitChange() =
            loadAll().distinctUntilChanged()

        @Query("SELECT * FROM $TABLE_NAME WHERE $ROW_ID = :id")
        fun getUser(id: Int): Flow<User>

        fun getUserDistinctUnitChange(id: Int) =
            getUser(id).distinctUntilChanged()
    }
}