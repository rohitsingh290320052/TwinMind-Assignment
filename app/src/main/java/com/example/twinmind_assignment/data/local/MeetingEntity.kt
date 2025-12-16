package com.example.twinmind_assignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meetings")
data class MeetingEntity(
    @PrimaryKey val sessionId: Long,
    val createdAt: Long,
    val preview: String
)
