package com.example.twinmind_assignment.di

import android.app.Application
import androidx.room.Room
import com.example.twinmind_assignment.data.db.AppDatabase
import com.example.twinmind_assignment.data.db.MeetingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "twinmind.db")
            .build()

    @Provides
    fun provideMeetingDao(db: AppDatabase): MeetingDao = db.meetingDao()
}
