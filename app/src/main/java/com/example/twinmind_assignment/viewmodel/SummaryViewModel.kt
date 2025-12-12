package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.twinmind_assignment.ui.state.SummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState = _uiState.asStateFlow()

    fun loadSession(sessionId: Long) {
        _uiState.value = SummaryUiState(
            title = "Meeting Summary $sessionId",
            summary = "Here is the summary for session $sessionId.",
            keyPoints = listOf("Point A", "Point B", "Point C"),
            actionItems = listOf("Follow-up", "Email team", "Prepare report")
        )
    }
}
