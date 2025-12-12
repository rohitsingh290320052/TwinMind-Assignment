package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.twinmind_assignment.ui.state.TranscriptUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TranscriptViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptUiState("", ""))
    val uiState = _uiState.asStateFlow()

    fun loadTranscript(text: String) {
        _uiState.value = TranscriptUiState(
            title = "",
            transcript = text
        )
    }

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun updateTranscript(newText: String) {
        _uiState.value = _uiState.value.copy(transcript = newText)
    }
}
