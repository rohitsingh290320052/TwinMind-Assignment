package com.example.twinmind_assignment.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twinmind_assignment.viewmodel.DashboardViewModel
import com.example.twinmind_assignment.ui.components.RecordingCard
import androidx.compose.foundation.lazy.items


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun DashboardScreen(
    onOpenRecorder: () -> Unit,
    onOpenSummary: (Long) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val meetings by viewModel.meetings.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

        Text("Your Meetings", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(meetings) { meeting ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenSummary(meeting.sessionId) }
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(
                            text = "Meeting ${meeting.sessionId}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = meeting.preview,
                            maxLines = 2,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onOpenRecorder,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Recording")
        }
    }
}

