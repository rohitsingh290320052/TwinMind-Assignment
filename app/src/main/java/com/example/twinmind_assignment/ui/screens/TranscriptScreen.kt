package com.example.twinmind_assignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twinmind_assignment.ui.navigation.Routes
import com.example.twinmind_assignment.viewmodel.TranscriptViewModel

@Composable
fun TranscriptScreen(
    sessionId: Long,
    textArg: String?,
    viewModel: TranscriptViewModel = hiltViewModel(),
    onContinue: () -> Unit
) {
    val decodedText = textArg?.let { Routes.decode(it) } ?: ""

    LaunchedEffect(decodedText) {
        viewModel.loadTranscript(decodedText)
    }

    val state = viewModel.uiState.collectAsState().value

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp)) {
                BasicTextField(
                    value = state.title,
                    onValueChange = viewModel::updateTitle
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Card(Modifier.weight(1f)) {
            Column(Modifier.padding(12.dp)) {
                Text("Transcript", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))

                BasicTextField(
                    value = state.transcript,
                    onValueChange = viewModel::updateTranscript,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Button(onClick = onContinue, modifier = Modifier.fillMaxWidth()) {
            Text("Generate Summary")
        }
    }
}
