package com.example.twinmind_assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LargeMicButton(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isRecording) Color(0xFFDC2626) else MaterialTheme.colorScheme.primary
    val icon = if (isRecording) Icons.Default.Stop else Icons.Default.Mic

    Surface(
        modifier = modifier
            .size(110.dp)
            .shadow(12.dp, CircleShape)
            .clickable { onClick() },
        shape = CircleShape,
        color = bgColor
    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .size(110.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(42.dp)
            )
        }
    }
}
