package com.example.twinmind_assignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeetingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeeting(meeting: MeetingEntity)

    @Query("SELECT * FROM meetings ORDER BY createdAt DESC")
    fun getMeetings(): Flow<List<MeetingEntity>>
}
