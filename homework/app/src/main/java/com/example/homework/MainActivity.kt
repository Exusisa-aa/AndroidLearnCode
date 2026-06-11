package com.example.homework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.homework.ui.chat.ChatScreen
import com.example.homework.ui.chat.ChatViewModel
import com.example.homework.ui.theme.HomeworkTheme

class MainActivity : ComponentActivity() {

    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeworkTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChatScreen(viewModel = chatViewModel)
                }
            }
        }
    }
}