package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.services.NewsItem
import com.example.myapp.services.NewsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
class NewsViewModel : ViewModel() {
    private val newsService = NewsService()
    
    private val _articles = MutableStateFlow<List<NewsItem>>(emptyList())
    val articles: StateFlow<List<NewsItem>> = _articles.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    fun fetchArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = newsService.fetchNews()
            result.onSuccess { articles ->
                _articles.value = articles
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Error desconocido"
            }
            
            _isLoading.value = false
        }
    }
}
