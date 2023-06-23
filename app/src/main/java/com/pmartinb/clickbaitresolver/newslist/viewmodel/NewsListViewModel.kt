package com.pmartinb.clickbaitresolver.newslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmartinb.clickbaitresolver.newslist.domain.model.News
import com.pmartinb.clickbaitresolver.newslist.domain.usecase.GetNewsListUseCase
import com.pmartinb.clickbaitresolver.newslist.ui.NewsListScreenState
import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.prompt.data.dto.response.ErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    val getNews: GetNewsListUseCase,
) : ViewModel() {

    private var _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>>
        get() = _news
    private var topic = MutableStateFlow<String>("")

    val state: StateFlow<NewsListScreenState> = channelFlow {
        topic.collect {
            if (it.isNotEmpty()) {
                val apiResponse = getNews(it)
                if (apiResponse is Result.Success) {
                    _news.value = apiResponse.value//.map { it.headline }
                    send(NewsListScreenState.Ready)
                } else if (apiResponse is Result.GenericError) {
                    listOf(
                        News(
                            headline = apiResponse.code.toString() + "" + (apiResponse.error as ErrorResponse.NewsErrorResponse).message,
                            description = "",
                        )
                    )
                    send(NewsListScreenState.Error)
                } else {
                    send(NewsListScreenState.Error)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = NewsListScreenState.Loading
    )

    fun setTopic(topic: String) {
        //TODO get from datastore
        this.topic.value = topic
    }

    fun onErrorRetryPressed() {

    }


}
