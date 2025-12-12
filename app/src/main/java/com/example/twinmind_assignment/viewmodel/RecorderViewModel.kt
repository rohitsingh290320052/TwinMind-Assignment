package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.domain.RecordAudioUseCase
import com.example.twinmind_assignment.domain.SummaryUseCase
import com.example.twinmind_assignment.domain.TranscribeUseCase
import com.example.twinmind_assignment.ui.state.RecorderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val recordAudio: RecordAudioUseCase,
    private val transcribeUseCase: TranscribeUseCase,
    private val summaryUseCase: SummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecorderUiState())
    val uiState = _uiState.asStateFlow()

    private var seconds = 0

    fun startRecording() {
        viewModelScope.launch {
            recordAudio.start()

            _uiState.value = _uiState.value.copy(
                isRecording = true,
                status = "Listening and taking notes..."
            )

            startTimer()
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            seconds = 0
            while (_uiState.value.isRecording) {
                delay(1000)
                seconds++
                val mm = (seconds / 60).toString().padStart(2, '0')
                val ss = (seconds % 60).toString().padStart(2, '0')

                _uiState.value = _uiState.value.copy(timer = "$mm:$ss")
            }
        }
    }

    fun stopRecording(onSessionReady: (Long) -> Unit) {
        viewModelScope.launch {
            val filePath = recordAudio.stop()

            val transcription = transcribeUseCase(filePath)

            val sessionId = System.currentTimeMillis()

            // Save transcription to DB later
            onSessionReady(sessionId)

            _uiState.value = _uiState.value.copy(
                isRecording = false,
                status = "Stopped"
            )
        }
    }

    fun togglePause() {
        _uiState.value = _uiState.value.copy(
            isRecording = !_uiState.value.isRecording,
            status = if (_uiState.value.isRecording) "Listening and taking notes..." else "Paused"
        )
    }
}