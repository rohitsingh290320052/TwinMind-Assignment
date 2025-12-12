package com.example.twinmind_assignment.ui.state

data class DashboardUiState(
    val sessions: List<SessionUi> = emptyList()
)

data class SessionUi(
    val id: Long,
    val title: String,
    val subtitle: String,
    val time: String
)
