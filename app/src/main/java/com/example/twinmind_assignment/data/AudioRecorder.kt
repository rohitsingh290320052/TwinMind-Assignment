package com.example.twinmind_assignment.data

import android.media.MediaRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class AudioRecorder @Inject constructor(
    private val app: android.app.Application
) {
    private var recorder: MediaRecorder? = null
    private lateinit var output: String

    suspend fun startRecording() = withContext(Dispatchers.IO) {
        val file = File(app.filesDir, "record_${System.currentTimeMillis()}.m4a")
        output = file.absolutePath

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(128000)
            setAudioSamplingRate(44100)
            setOutputFile(output)
            prepare()
            start()
        }
    }

    suspend fun stopRecording(): String = withContext(Dispatchers.IO) {
        recorder?.apply {
            try { stop() } catch (e: Exception) { e.printStackTrace() }
            release()
        }
        recorder = null
        output
    }
}
