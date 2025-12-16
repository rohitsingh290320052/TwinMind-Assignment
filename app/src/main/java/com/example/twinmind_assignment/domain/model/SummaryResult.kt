package com.example.twinmind_assignment.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SummaryResult(
    val title: String,
    val summary: String,
    val keyPoints: List<String>,
    val actionItems: List<String>
)

