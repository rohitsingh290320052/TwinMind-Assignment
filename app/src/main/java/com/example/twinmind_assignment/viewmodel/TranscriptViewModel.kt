package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TranscriptViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptUiState())
    val uiState = _uiState.asStateFlow()

    fun loadSession(sessionId: Long) {
        _uiState.value = TranscriptUiState(
            title = "Meeting $sessionId",
            transcript = "This is the transcribed text for session $sessionId..."
        )
    }

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun updateTranscript(newText: String) {
        _uiState.value = _uiState.value.copy(transcript = newText)
    }
}
