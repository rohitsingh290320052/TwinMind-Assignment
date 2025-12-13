package com.example.twinmind_assignment.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "summaries")
data class SummaryEntity(
    @PrimaryKey val meetingId: Long,
    val title: String,
    val summary: String,
    val keyPointsJson: String,
    val actionItemsJson: String
)
