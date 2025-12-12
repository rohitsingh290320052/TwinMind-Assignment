package com.example.twinmind_assignment.ui.state

data class SummaryUiState(
    val title: String = "",
    val summary: String = "",
    val keyPoints: List<String> = emptyList(),
    val actionItems: List<String> = emptyList(),
    val isLoading: Boolean = false
)
