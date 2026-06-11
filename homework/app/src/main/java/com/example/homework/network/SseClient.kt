package com.example.homework.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

sealed class SseEvent {
    data class Data(val chunk: String) : SseEvent()
    data class Error(val message: String) : SseEvent()
    data object Done : SseEvent()
}

class SseClient {

    companion object {
        const val DEFAULT_BASE_URL = "http://10.0.2.2:8080/ai/chat"
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()

    fun streamChat(prompt: String, chatId: String, baseUrl: String = DEFAULT_BASE_URL): Flow<SseEvent> =
        flow {
            val encodedPrompt = URLEncoder.encode(prompt, "UTF-8")
            val url = "$baseUrl?prompt=$encodedPrompt&chatId=$chatId"

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            val response = try {
                client.newCall(request).execute()
            } catch (e: IOException) {
                emit(SseEvent.Error("网络连接失败: ${e.message}"))
                emit(SseEvent.Done)
                return@flow
            }

            if (!response.isSuccessful) {
                emit(SseEvent.Error("HTTP ${response.code}: ${response.message}"))
                emit(SseEvent.Done)
                return@flow
            }

            val body = response.body ?: run {
                emit(SseEvent.Error("empty response body"))
                emit(SseEvent.Done)
                return@flow
            }

            try {
                val input = body.byteStream()
                val buffer = ByteArray(16)

                while (true) {
                    val count = input.read(buffer)
                    if (count == -1) break
                    val chunk = String(buffer, 0, count, Charsets.UTF_8)
                    emit(SseEvent.Data(chunk))
                    delay(20)
                }
            } catch (e: IOException) {
                emit(SseEvent.Error("流读取中断: ${e.message}"))
            } catch (e: Exception) {
                emit(SseEvent.Error("未知错误: ${e.message}"))
            } finally {
                response.close()
                emit(SseEvent.Done)
            }
        }.flowOn(Dispatchers.IO)
}
