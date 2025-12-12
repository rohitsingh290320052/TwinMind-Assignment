package com.example.twinmind_assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.ArrowForward
import androidx.compose.material3.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

@Composable
fun LargeMicButton(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    onClick: () -> Unit
) {
    val bg = if (isRecording) Color(0xFFDC2626) else MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .size(96.dp)
            .shadow(8.dp, CircleShape)
            .background(bg, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Mic,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun RecordingCard(
    title: String,
    subtitle: String,
    time: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            // left circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFEEF2FF), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Mic, contentDescription = null, tint = Color(0xFF3B82F6))
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(2.dp))
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = Color(0xFF556987))
            }
            Text(text = time, style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
            Spacer(Modifier.width(12.dp))
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color(0xFF94A3B8))
        }
    }
}
