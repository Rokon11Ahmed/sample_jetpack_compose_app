package com.example.currentnews.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentnews.core.common.DispatcherProvider
import com.example.currentnews.core.utils.Resource
import com.example.currentnews.domain.model.News
import com.example.currentnews.domain.repository.NewsRepository
import com.example.currentnews.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel  @Inject constructor(
    private val repository: NewsRepository,
    private val dispatcher: DispatcherProvider
): ViewModel()  {

    private var isLoading = MutableStateFlow(false)
    private val _news: MutableState<News> = mutableStateOf(News(0,"", "", "", "", "", "", ""))
    val news: State<News> = _news

    fun getSingleNewsById(newsId: String){
        viewModelScope.launch(dispatcher.io){
            repository.getSingleNewsById(newsId).collect {result ->
                when (result) {
                    is Resource.Error -> {
                        isLoading.value = false
                        if (result.data != null) {
                            withContext(dispatcher.main){
                                _news.value = result.data
                            }

                        } else {
                        }
                    }
                    is Resource.Loading -> {
                        isLoading.value = true
                    }
                    is Resource.Success -> {
                        isLoading.value = false
                        if (result.data != null) {
                            withContext(dispatcher.main){
                                _news.value = result.data
                            }
                        }
                    }
                }
            }
        }
    }
}