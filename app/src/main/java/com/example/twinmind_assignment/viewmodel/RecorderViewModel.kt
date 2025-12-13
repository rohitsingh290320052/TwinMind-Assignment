package com.example.twinmind_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.data.db.MeetingDao
import com.example.twinmind_assignment.data.db.MeetingEntity
import com.example.twinmind_assignment.data.db.TranscriptChunkEntity
import com.example.twinmind_assignment.domain.RecordAudioUseCase
import com.example.twinmind_assignment.domain.TranscribeUseCase
import com.example.twinmind_assignment.ui.state.RecorderUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val recordAudio: RecordAudioUseCase,
    private val transcribeUseCase: TranscribeUseCase,
    private val dao: MeetingDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecorderUiState())
    val uiState = _uiState.asStateFlow()

    // Notify navigation when session is ready
    private val _processingDone = MutableSharedFlow<Long>()
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
                _uiState.value = _uiState.value.copy(
                    timer = "%02d:%02d".format(seconds / 60, seconds % 60)
                )
            }
        }
    }

    fun stopRecording() {
        viewModelScope.launch {
            val filePath = recordAudio.stop()
            val transcription = transcribeUseCase(filePath)

            val sessionId = System.currentTimeMillis()

            // ✅ Save meeting
            dao.insertMeeting(
                MeetingEntity(
                    id = sessionId,
                    title = "Meeting $sessionId",
                    createdAt = sessionId,
                    status = "DONE"
                )
            )

            // ✅ Save transcript (chunkIndex = 0 for now)
            dao.insertChunk(
                TranscriptChunkEntity(
                    meetingId = sessionId,
                    chunkIndex = 0,
                    text = transcription
                )
            )

            _uiState.value = _uiState.value.copy(
                isRecording = false,
                status = "Stopped"
            )

            _processingDone.emit(sessionId)
        }
    }

    fun togglePause() {
        _uiState.value = _uiState.value.copy(
            isRecording = !_uiState.value.isRecording,
            status = if (_uiState.value.isRecording)
                "Listening and taking notes..." else "Paused"
        )
    }
}
