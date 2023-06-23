package com.pmartinb.clickbaitresolver.web.ui

import androidx.lifecycle.ViewModel
import com.pmartinb.clickbaitresolver.newslist.ui.NewsListScreenState
import com.pmartinb.clickbaitresolver.prompt.domain.usecase.GetCompletionUseCase
import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.prompt.data.dto.response.ErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    var getCompletion: GetCompletionUseCase
) : ViewModel() {

    private val _currentHeadline = MutableStateFlow<String>("")
    val currentHeadline: StateFlow<String>
        get() = _currentHeadline


    fun getHeadline() {
        CoroutineScope(Dispatchers.Main).launch {
            var apiResponse = getCompletion("Última hora:")
            _currentHeadline.emit("Loading headline ...")
            var resp = if (apiResponse is Result.Success) {
                apiResponse.value
            } else if (apiResponse is Result.GenericError) {
                apiResponse.code.toString() + "" + (apiResponse.error as ErrorResponse.GptErrorResponse).error.message
            } else {
                "network error"
            }
            _currentHeadline.emit(
                resp
            )
        }
    }

    fun sendNewsContentClicked() {
        getHeadline()
    }

}
