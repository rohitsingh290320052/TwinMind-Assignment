package com.example.twinmind_assignment.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TwinMind-like colors (Option A)
private val TwinLightPrimary = Color(0xFFEF4444) // vivid red mic
private val TwinLightOnPrimary = Color.White
private val TwinLightBackground = Color(0xFFFFFFFF)
private val TwinLightSurface = Color(0xFFF8FAFC)
private val TwinLightOnSurface = Color(0xFF0F172A)
private val TwinLightSecondary = Color(0xFF3B82F6) // subtle blue accent

private val LightColorScheme = lightColorScheme(
    primary = TwinLightPrimary,
    onPrimary = TwinLightOnPrimary,
    secondary = TwinLightSecondary,
    background = TwinLightBackground,
    surface = TwinLightSurface,
    onSurface = TwinLightOnSurface
)

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // We'll keep a light-only TwinMind look because TwinMind is light focused
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography(),
        shapes = Shapes(
            small = androidx.compose.foundation.shape.RoundedCornerShape(8),
            medium = androidx.compose.foundation.shape.RoundedCornerShape(14),
            large = androidx.compose.foundation.shape.RoundedCornerShape(20)
        ),
        content = content
    )
}
