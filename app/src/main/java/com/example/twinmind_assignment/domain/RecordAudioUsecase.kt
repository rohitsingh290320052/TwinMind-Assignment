package com.example.twinmind_assignment.domain

import com.example.twinmind_assignment.data.AudioRecorder
import javax.inject.Inject

class RecordAudioUseCase @Inject constructor(
    private val recorder: AudioRecorder
) {
    suspend fun start() = recorder.startRecording()
    suspend fun stop(): String = recorder.stopRecording()
}
