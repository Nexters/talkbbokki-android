package com.hammer.talkbbokki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
    Scaffold { padding ->
        TopicListScreen(
            Modifier
                .padding(padding)
                .background(color = Color(0xFF1E1E1E))
        )
    }
}