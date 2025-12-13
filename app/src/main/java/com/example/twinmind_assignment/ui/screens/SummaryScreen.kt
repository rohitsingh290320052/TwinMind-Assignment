package com.example.twinmind_assignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twinmind_assignment.data.TranscriptionCache
import com.example.twinmind_assignment.ui.navigation.Routes.transcript
import com.example.twinmind_assignment.viewmodel.SummaryViewModel

@Composable
fun SummaryScreen(
    sessionId: Long,
    viewModel: SummaryViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value

    LaunchedEffect(sessionId) {
        viewModel.loadOrGenerate(sessionId)
    }

    when {
        state.loading -> {
            CircularProgressIndicator()
            Text("Generating summary...")
        }

        state.error != null -> {
            Text("Error: ${state.error}")
        }

        else -> {
            // render summary
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
