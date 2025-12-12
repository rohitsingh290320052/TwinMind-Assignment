package com.example.twinmind_assignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.twinmind_assignment.viewmodel.RecorderViewModel

@Composable
fun ProcessingScreen(
    viewModel: RecorderViewModel,
    onDone: (Long, String) -> Unit
) {
    LaunchedEffect(true) {
        viewModel.processingDone.collect { result ->
            onDone(result.sessionId, result.transcription)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(Modifier.height(16.dp))
        Text("Processing your memory...", style = MaterialTheme.typography.titleMedium)
        Text(
            "This may take around 30 seconds",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}
