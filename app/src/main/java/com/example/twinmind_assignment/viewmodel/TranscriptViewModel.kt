package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.data.RemoteRepository
import com.example.twinmind_assignment.ui.state.TranscriptUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranscriptViewModel @Inject constructor(
    private val repository: RemoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptUiState())
    val uiState = _uiState.asStateFlow()

    fun loadSession(sessionId: Long) {
        viewModelScope.launch {

            // Full transcript string from Room (correct order)
            val transcriptText = repository.getFullTranscript(sessionId)

            _uiState.value = TranscriptUiState(
                title = "Meeting $sessionId",
                transcript = transcriptText
            )
        }
    }

    fun updateTitle(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun updateTranscript(newText: String) {
        _uiState.value = _uiState.value.copy(transcript = newText)
    }
}
