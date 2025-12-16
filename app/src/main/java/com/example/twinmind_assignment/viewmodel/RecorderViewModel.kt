package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.data.RemoteRepository
import com.example.twinmind_assignment.domain.RecordAudioUseCase
import com.example.twinmind_assignment.ui.state.RecorderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val recordAudio: RecordAudioUseCase,
    private val repository: RemoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecorderUiState())
    val uiState = _uiState.asStateFlow()

    private val _processingDone = MutableSharedFlow<Pair<Long, String>>()
    val processingDone = _processingDone

    fun startRecording() {
        viewModelScope.launch {
            recordAudio.start()
            _uiState.value = _uiState.value.copy(
                isRecording = true,
                status = "Listening and taking notes..."
            )
        }
    }

    fun stopRecording() {
        viewModelScope.launch {
            val filePath = recordAudio.stop()
            val sessionId = System.currentTimeMillis()

            _uiState.value = _uiState.value.copy(
                status = "Processing your memory...",
                isRecording = false
            )

            val transcription = repository.transcribeAndSave(
                sessionId = sessionId,
                filePath = filePath
            )

            _processingDone.emit(sessionId to transcription)
        }
    }
}
