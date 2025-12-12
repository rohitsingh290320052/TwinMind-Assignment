package com.example.twinmind_assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twinmind_assignment.domain.RecordAudioUseCase
import com.example.twinmind_assignment.domain.SummaryUseCase
import com.example.twinmind_assignment.domain.TranscribeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val recordAudio: RecordAudioUseCase,
    private val transcribe: TranscribeUseCase,
    private val summarize: SummaryUseCase
) : ViewModel() {

    private val _isRecording = MutableStateFlow(false)
    val isRecording = _isRecording.asStateFlow()

    private val _transcription = MutableStateFlow("")
    val transcription = _transcription.asStateFlow()

    private val _summary = MutableStateFlow("")
    val summary = _summary.asStateFlow()

    fun startRecording() {
        viewModelScope.launch {
            recordAudio.start()
            _isRecording.value = true
        }
    }

    fun stopRecording() {
        viewModelScope.launch {
            val filePath = recordAudio.stop()
            _isRecording.value = false

            val text = transcribe(filePath)
            _transcription.value = text

            _summary.value = summarize(text)
        }
    }
}
