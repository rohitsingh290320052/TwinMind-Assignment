package com.example.twinmind_assignment.ui.state

data class RecorderUiState(
    val isRecording: Boolean = false,
    val timer: String = "00:00",
    val status: String = "Ready to record"
)
