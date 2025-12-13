package com.example.twinmind_assignment.ui.state

import com.example.twinmind_assignment.data.db.SummaryEntity

data class SummaryUiState(
    val title: String = "",
    val summary: String = "",
    val keyPoints: List<String> = emptyList(),
    val actionItems: List<String> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
) {
    companion object {
        fun fromEntity(entity: SummaryEntity) = SummaryUiState(
            title = entity.title,
            summary = entity.summary,
            keyPoints = entity.keyPoints,
            actionItems = entity.actionItems,
            loading = false
        )
    }
}
