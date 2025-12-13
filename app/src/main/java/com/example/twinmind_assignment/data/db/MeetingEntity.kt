package com.example.twinmind_assignment.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meetings")
data class MeetingEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val createdAt: Long,
    val status: String // RECORDING / TRANSCRIBING / DONE
)
