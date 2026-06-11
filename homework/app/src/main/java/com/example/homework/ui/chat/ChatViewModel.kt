package com.example.homework.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework.network.SseClient
import com.example.homework.network.SseEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: Long,
    val role: String,
    val content: String
)

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isStreaming: Boolean = false,
    val chatId: String = "1"
)

class ChatViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val sseClient = SseClient()
    private var streamJob: Job? = null
    private var nextMessageId = 0L

    fun onInputChange(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun onChatIdChange(chatId: String) {
        _uiState.update { it.copy(chatId = chatId) }
    }

    fun sendMessage() {
        val state = _uiState.value
        val prompt = state.inputText.trim()
        if (prompt.isEmpty()) return
        if (state.isStreaming) return

        val userMessage = ChatMessage(id = nextMessageId++, role = "user", content = prompt)
        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                inputText = "",
                isStreaming = true
            )
        }

        val aiMessage = ChatMessage(id = nextMessageId++, role = "ai", content = "")
        _uiState.update { it.copy(messages = it.messages + aiMessage) }

        streamJob = viewModelScope.launch {
            sseClient.streamChat(prompt = prompt, chatId = state.chatId)
                .collect { event ->
                    when (event) {
                        is SseEvent.Data -> {
                            _uiState.update {
                                val updated = it.messages.toMutableList()
                                val lastIdx = updated.lastIndex
                                updated[lastIdx] = updated[lastIdx].copy(
                                    content = updated[lastIdx].content + event.chunk
                                )
                                it.copy(messages = updated)
                            }
                        }
                        is SseEvent.Error -> {
                            _uiState.update {
                                val updated = it.messages.toMutableList()
                                val lastIdx = updated.lastIndex
                                updated[lastIdx] = updated[lastIdx].copy(
                                    content = updated[lastIdx].content +
                                        "\n\n[错误: ${event.message}]"
                                )
                                it.copy(messages = updated, isStreaming = false)
                            }
                        }
                        is SseEvent.Done -> {
                            _uiState.update { it.copy(isStreaming = false) }
                        }
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        streamJob?.cancel()
    }
}
