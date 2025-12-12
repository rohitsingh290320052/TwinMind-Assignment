package com.example.twinmind_assignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twinmind_assignment.data.TranscriptionCache
import com.example.twinmind_assignment.viewmodel.SummaryViewModel

@Composable
fun SummaryScreen(
    sessionId: Long,
    viewModel: SummaryViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val transcript = TranscriptionCache.get(sessionId)
    val state = viewModel.uiState.collectAsState().value

    LaunchedEffect(sessionId) {
        if (!viewModel.uiState.value.loading && state.summary.isBlank()) {
            viewModel.generate(transcript)
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        when {
            state.loading -> {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
                Text("Generating summary...")
            }

            state.error != null -> {
                Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { viewModel.generate(transcript) }) {
                    Text("Retry")
                }
            }

            else -> {
                SummaryCard("Title", state.title)
                Spacer(Modifier.height(12.dp))
                SummaryCard("Summary", state.summary)
                Spacer(Modifier.height(12.dp))
                SummaryListCard("Key Points", state.keyPoints)
                Spacer(Modifier.height(12.dp))
                SummaryListCard("Action Items", state.actionItems)
                Spacer(Modifier.height(24.dp))

                Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                    Text("Back")
                }
            }
        }
    }
}

@Composable
fun SummaryCard(title: String, body: String) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(body)
        }
    }
}

@Composable
fun SummaryListCard(title: String, items: List<String>) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            items.forEach {
                Text("• $it", Modifier.padding(vertical = 2.dp))
            }
        }
    }
}
