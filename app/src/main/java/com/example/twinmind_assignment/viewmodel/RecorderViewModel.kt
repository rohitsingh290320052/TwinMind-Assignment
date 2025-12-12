package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.data.TranscriptionCache
import com.example.twinmind_assignment.domain.RecordAudioUseCase
import com.example.twinmind_assignment.domain.SummaryUseCase
import com.example.twinmind_assignment.domain.TranscribeUseCase
import com.example.twinmind_assignment.ui.state.RecorderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class RecordingResult(
    val sessionId: Long,
    val transcription: String
)

@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val recordAudio: RecordAudioUseCase,
    private val transcribeUseCase: TranscribeUseCase,
    private val summaryUseCase: SummaryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecorderUiState())
    val uiState = _uiState.asStateFlow()

    private val _processingDone = MutableSharedFlow<RecordingResult>()
    val processingDone = _processingDone

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

    fun stopRecording() {
        viewModelScope.launch {
            val filePath = recordAudio.stop()
            println("🟩 RecorderViewModel: Transcribing file: $filePath")

            val transcription = transcribeUseCase(filePath)
            println("🟧 RecorderViewModel: Transcription result: $transcription")

            val sessionId = System.currentTimeMillis()

            TranscriptionCache.save(sessionId, transcription)


            _processingDone.emit(
                RecordingResult(sessionId, transcription)
            )

            _uiState.value = _uiState.value.copy(
                isRecording = false,
                status = "Stopped"
            )

            println("Recording saved at $filePath, size = ${File(filePath).length()} bytes")
        }
    }

    fun togglePause() {
        _uiState.value = _uiState.value.copy(
            isRecording = !_uiState.value.isRecording,
            status = if (_uiState.value.isRecording) "Listening and taking notes..." else "Paused"
        )
    }
}
