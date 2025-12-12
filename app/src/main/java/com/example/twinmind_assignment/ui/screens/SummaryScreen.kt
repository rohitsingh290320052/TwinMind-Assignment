package com.example.twinmind_assignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.twinmind_assignment.viewmodel.SummaryViewModel

@Composable
fun SummaryScreen(
    sessionId: Long,
    viewModel: SummaryViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.loadSession(sessionId) }

    val state = viewModel.uiState.collectAsState().value

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp)) {
                Text(state.title, style = MaterialTheme.typography.titleLarge)
            }
        }

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

