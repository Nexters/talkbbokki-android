package com.hammer.talkbbokki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                Sample()
            }
        }
    }
}


@Composable
private fun Sample() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("horiz_pager") },
                backgroundColor = MaterialTheme.colors.surface,
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        TopicListScreen(
            Modifier
                .padding(padding)
                .background(color = Color(0xFF518EF5)))
    }
}