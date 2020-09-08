package com.example.practica.di

import android.content.Context
import androidx.room.Room
import com.example.practica.data.TaskDataBase
import com.example.practica.data.TaskSchema.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton



@InstallIn(ApplicationComponent::class)
@Module
object RoomModule{
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, TaskDataBase::class.java, DB_NAME)
            .build()

    @Singleton
    @Provides
    fun provideUserStore(db: TaskDataBase) =
        db.userStore()
}