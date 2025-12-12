package com.example.twinmind_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twinmind_assignment.ui.navigation.AppNavHost
import com.example.twinmind_assignment.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme()
        }
    }
}

@Composable
fun Theme() {
    Theme {
        AppNavHost()
    }
}
