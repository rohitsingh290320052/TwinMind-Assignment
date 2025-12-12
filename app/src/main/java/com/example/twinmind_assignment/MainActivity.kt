package com.example.twinmind_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twinmind_assignment.ui.theme.TwinMindTheme
import com.example.twinmind_assignment.ui.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwinMindApp()
        }
    }
}

@Composable
fun TwinMindApp() {
    TwinMindTheme {
        // entrypoint for nav + screens
        AppNavHost()
    }
}
