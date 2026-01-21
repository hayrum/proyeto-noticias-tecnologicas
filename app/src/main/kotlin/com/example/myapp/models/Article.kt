package com.example.myapp.models

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class Article(
    @SerialName("source")
    val source: Source,
    
    @SerialName("author")
    val author: String?,
    
    @SerialName("title")
    val title: String,
    
    @SerialName("description")
    val description: String?,
    
    @SerialName("url")
    val url: String,
    
    @SerialName("urlToImage")
    val urlToImage: String?,
    
    @SerialName("publishedAt")
    val publishedAt: String,
    
    @SerialName("content")
    val content: String?
) : java.io.Serializable {
    // Implementar Identifiable para Compose
    val id: String
        get() = url
}

@InternalSerializationApi @Serializable
data class Source(
    @SerialName("id")
    val id: String?,
    
    @SerialName("name")
    val name: String
) : java.io.Serializable

@InternalSerializationApi @Serializable
data class NewsResponse(
    @SerialName("status")
    val status: String,
    
    @SerialName("totalResults")
    val totalResults: Int,
    
    @SerialName("articles")
    val articles: List<Article>
) : java.io.Serializable
