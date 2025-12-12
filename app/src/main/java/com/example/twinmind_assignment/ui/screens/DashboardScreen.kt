package com.example.twinmind_assignment.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twinmind_assignment.viewmodel.DashboardViewModel
import com.example.twinmind_assignment.ui.components.RecordingCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onOpenRecorder: () -> Unit,
    onOpenSummary: (Long) -> Unit
) {
    val state = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memories") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onOpenRecorder) {
                Icon(Icons.Default.Mic, null)
            }
        }
    ) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding)) {
            items(state.sessions.size) { i ->
                val session = state.sessions[i]
                RecordingCard(
                    title = session.title,
                    subtitle = session.subtitle,
                    time = session.time,
                    onClick = { onOpenSummary(session.id) }
                )
            }
        }
    }
}

