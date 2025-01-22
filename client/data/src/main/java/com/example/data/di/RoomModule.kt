package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.data.db.ResumeDatabase
import com.example.data.db.dao.ResumeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    companion object {
        private const val DB_NAME = "database"
    }

    @Singleton
    @Provides
    fun provideResumeDb(application: Application): ResumeDatabase {
        return Room.databaseBuilder(
            application,
            ResumeDatabase::class.java,
            DB_NAME
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: ResumeDatabase): ResumeDao {
        return database.resumeDao()
    }
}

