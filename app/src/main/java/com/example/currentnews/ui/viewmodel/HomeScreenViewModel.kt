package com.example.currentnews.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentnews.R
import com.example.currentnews.core.common.DispatcherProvider
import com.example.currentnews.core.utils.Resource
import com.example.currentnews.di.AppModule.ResourcesProvider
import com.example.currentnews.domain.model.News
import com.example.currentnews.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val dispatcher: DispatcherProvider,
    private val resourcesProvider: ResourcesProvider
): ViewModel() {

    sealed class NewsUiEvent {
        object Loading : NewsUiEvent()
        class Error(val data: List<News>?, val msg: String?) : NewsUiEvent()
        class Success(val data: List<News>, val msg: String?) : NewsUiEvent()
    }

    val queryText = mutableStateOf(resourcesProvider.getString(R.string.IDS_SEARCH_HERE))
    var isLoading = MutableStateFlow(false)
    var searchText = MutableStateFlow(resourcesProvider.getString(R.string.IDS_NEWS))
    private val _newsStateFlow =
        MutableStateFlow<NewsUiEvent>(NewsUiEvent.Loading)
    val newsStateFlow: MutableStateFlow<NewsUiEvent>
        get() = _newsStateFlow

    private val _newsList: MutableState<List<News>> = mutableStateOf(arrayListOf())
    val newsList: State<List<News>> = _newsList


    fun getNewsHeadlines() {
        viewModelScope.launch(dispatcher.io) {
            withContext(dispatcher.main){
                if (!queryText.equals(resourcesProvider.getString(R.string.IDS_SEARCH_HERE))){searchText.value = queryText.value}
            }
            repository.getAllNewsHeadlines(searchText.value).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        isLoading.value = false
                        if (result.data != null) {
                            _newsList.value = result.data
                            _newsStateFlow.emit(
                                NewsUiEvent.Error(
                                    data = result.data,
                                    msg = result.message
                                )
                            )
                        } else {
                            _newsStateFlow.emit(
                                NewsUiEvent.Error(
                                    data = null,
                                    msg = result.message
                                )
                            )
                        }
                    }
                    is Resource.Loading -> {
                        isLoading.value = true
                        _newsStateFlow.emit(NewsUiEvent.Loading)
                    }
                    is Resource.Success -> {
                        isLoading.value = false
                        if (result.data != null) {
                            _newsList.value = result.data
                            _newsStateFlow.emit(
                                NewsUiEvent.Success(
                                    data = result.data,
                                    msg = "Success"
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun onQueryChange(query: String){
        this.queryText.value = query
    }
}