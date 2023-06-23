package com.pmartinb.clickbaitresolver.prompt.data.dto.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class ErrorResponse {

    @JsonClass(generateAdapter = true)
    data class GptErrorResponse(
        @Json(name = "error") val error: GptErrorInfo,
    ) : ErrorResponse()

    @JsonClass(generateAdapter = true)
    data class GptErrorInfo(
        @Json(name = "message") val message: String,
        @Json(name = "type") val type: String,
        @Json(name = "param") val param: Int?,
        @Json(name = "code") val code: Int?,
    )

    @JsonClass(generateAdapter = true)
    data class NewsErrorResponse(
        @Json(name = "status") val status: String,
        @Json(name = "code") val code: String,
        @Json(name = "message") val message: String,
    ) : ErrorResponse()
}

sealed class ErrorContent {
    data class SimpleErrorContent(val message: String = "Error") : ErrorContent()
}