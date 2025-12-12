package com.example.twinmind_assignment.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.twinmind_assignment.RecorderViewModel

@Composable
fun RecorderScreen(viewModel: RecorderViewModel) {
    val isRecording by viewModel.isRecording.collectAsState()
    val transcription by viewModel.transcription.collectAsState()
    val summary by viewModel.summary.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                if (isRecording) viewModel.stopRecording()
                else viewModel.startRecording()
            }
        ) {
            Text(if (isRecording) "Stop Recording" else "Start Recording")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Transcription:")
        Text(transcription)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Summary:")
        Text(summary)
    }
}
