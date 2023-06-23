package com.pmartinb.clickbaitresolver.prompt.extensions

import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.prompt.data.dto.response.ErrorResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException

sealed class ErrorType {
    object GptError : ErrorType()
    object NewsError : ErrorType()
}

fun Throwable.getErrorResponse(type: ErrorType) = when (this) {
    is IOException -> Result.NetworkError
    is HttpException -> {
        val errorResponse = when (type) {
            ErrorType.GptError -> extractGptErrorResponse()
            ErrorType.NewsError -> extractNewsErrorResponse()
        }
        Result.GenericError(this.code(), errorResponse)
    }

    else -> {
        printStackTrace()
        Result.GenericError(null, null)
    }
}

fun HttpException.extractGptErrorResponse(): ErrorResponse.GptErrorResponse? {
    val adapter: JsonAdapter<ErrorResponse.GptErrorResponse> =
        Moshi.Builder().build().adapter(ErrorResponse.GptErrorResponse::class.java).lenient()
    response()?.run {
        errorBody()?.let {
            val errorJson = it.string()
            val parseado = adapter.fromJson(errorJson)
            return parseado
        }
    }
    return null
}

fun HttpException.extractNewsErrorResponse(): ErrorResponse.NewsErrorResponse? {
    val adapter: JsonAdapter<ErrorResponse.NewsErrorResponse> =
        Moshi.Builder().build().adapter(ErrorResponse.NewsErrorResponse::class.java).lenient()
    response()?.run {
        errorBody()?.let {
            val errorJson = it.string()
            val parseado = adapter.fromJson(errorJson)
            return parseado
        }
    }
    return null
}

