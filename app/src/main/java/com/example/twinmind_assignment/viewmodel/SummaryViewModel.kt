package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.data.RemoteRepository
import com.example.twinmind_assignment.domain.SummaryUseCase
import com.example.twinmind_assignment.ui.state.SummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val repository: RemoteRepository,
    private val summaryUseCase: SummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SummaryUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    fun loadAndGenerate(sessionId: Long) {
        viewModelScope.launch {
            try {
                _uiState.value = SummaryUiState(loading = true)

                // ✅ ALWAYS load real transcript from Room
                val transcript = repository.getFullTranscript(sessionId)

                if (transcript.isBlank()) {
                    throw IllegalStateException("Transcript is empty")
                }

                // ✅ Generate summary from REAL transcript
                val result = summaryUseCase(transcript)

                _uiState.value = SummaryUiState(
                    title = result.title,
                    summary = result.summary,
                    keyPoints = result.keyPoints,
                    actionItems = result.actionItems,
                    loading = false
                )
            } catch (e: Exception) {
                _uiState.value = SummaryUiState(
                    error = e.message ?: "Failed to generate summary",
                    loading = false
                )
            }
        }
    }
}
