package com.gmail.ngampiosauvet.task.di

import android.content.Context
import androidx.room.Room
import com.gmail.ngampiosauvet.task.data.source.local.TaskDao
import com.gmail.ngampiosauvet.task.data.source.local.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {


    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_database",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Singleton
    @DefaultDispatcher
    @Provides
    fun provideDispatcher():CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideExternalScope(): CoroutineScope {
        return CoroutineScope(Job() + Dispatchers.Main)
    }

}
@Qualifier
annotation class DefaultDispatcher
