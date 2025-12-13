package com.example.twinmind_assignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        MeetingEntity::class,
        TranscriptChunkEntity::class,
        SummaryEntity::class
    ],
    version = 1,
    exportSchema = false
)


abstract class AppDatabase : RoomDatabase() {
    abstract fun meetingDao(): MeetingDao
}
