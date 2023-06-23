package com.pmartinb.clickbaitresolver.prompt.ui

import androidx.lifecycle.ViewModel
import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.prompt.data.dto.response.ErrorResponse
import com.pmartinb.clickbaitresolver.prompt.domain.usecase.GetCompletionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromptViewModel @Inject constructor(
    var getCompletion: GetCompletionUseCase
) : ViewModel() {

    private val _currentResponse = MutableStateFlow<String>("")
    val currentResponse: StateFlow<String>
        get() = _currentResponse

    private val _readNewsButton = MutableStateFlow<Boolean>(false)
    val readNewsButton: StateFlow<Boolean>
        get() = _readNewsButton

    init {
//        getResponse()
    }

    fun getResponse() {
        CoroutineScope(Dispatchers.Main).launch {
            _currentResponse.emit("cargando")
            var apiResponse = getCompletion("Todos")
            var resp = if (apiResponse is Result.Success) {
                apiResponse.value
            } else if (apiResponse is Result.GenericError) {
                apiResponse.code.toString() + "" + (apiResponse.error as ErrorResponse.GptErrorResponse).error.message
            } else {
                "network error"
            }
            _currentResponse.emit(
                resp
            )
        }
    }

    fun checkUrlClicked() {
        CoroutineScope(Dispatchers.Main).launch {
            _readNewsButton.emit(!_readNewsButton.value)
        }
    }
}
