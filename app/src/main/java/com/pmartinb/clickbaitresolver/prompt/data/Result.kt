package com.pmartinb.clickbaitresolver.prompt.data

import com.pmartinb.clickbaitresolver.prompt.data.dto.response.ErrorContent
import com.pmartinb.clickbaitresolver.prompt.data.dto.response.ErrorResponse


sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class GenericError(
        val code: Int? = null,
        val error: ErrorResponse? = null,
        val errorContent: ErrorContent? = null
    ) : Result<Nothing>()
    object NetworkError : Result<Nothing>()

    fun getOrNull(): T? = when (this) {
        is Success -> value
        else -> null
    }
}
