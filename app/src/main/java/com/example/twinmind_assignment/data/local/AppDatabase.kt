package com.example.twinmind_assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TranscriptEntity::class,
        MeetingEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transcriptDao(): TranscriptDao
    abstract fun meetingDao(): MeetingDao
}
