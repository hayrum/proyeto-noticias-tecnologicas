package com.example.myapp.services

import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import com.example.myapp.config.AppConfig

@InternalSerializationApi @Serializable
data class NewsItem(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val precio: Double
)

@OptIn(InternalSerializationApi::class)
class NewsService {
    private val json = Json { ignoreUnknownKeys = true }
    
    suspend fun fetchNews(): Result<List<NewsItem>> = withContext(Dispatchers.IO) {
        try {
            val connection = (URL(AppConfig.GET_PRODUCTS).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 5000
                readTimeout = 5000
            }
            
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val newsList = json.decodeFromString<List<NewsItem>>(response)
            
            Result.success(newsList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}