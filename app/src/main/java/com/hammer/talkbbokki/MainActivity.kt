package com.hammer.talkbbokki

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hammer.talkbbokki.presentation.topics.TopicListUiState
import com.hammer.talkbbokki.presentation.topics.TopicListViewModel
import com.hammer.talkbbokki.ui.theme.IcebreakerTheme
import androidx.compose.ui.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<TopicListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IcebreakerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }

        dataObserver()
    }

    private fun dataObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topicList.collect {
                    when(it) {
                        is TopicListUiState.Success -> Toast.makeText(this@MainActivity, it.list.map { it.name }.toString(), Toast.LENGTH_SHORT).show()
                        is TopicListUiState.Loading -> Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
                        is TopicListUiState.Error -> Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IcebreakerTheme {
        Greeting("Android")
    }
}