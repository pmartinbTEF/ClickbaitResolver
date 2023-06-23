package com.pmartinb.clickbaitresolver.newslist.data.dto.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class NewsErrorResponse2 {

    @JsonClass(generateAdapter = true)
    data class NewsErrorInfo(
        @Json(name = "status") val status: String,
        @Json(name = "code") val code: String,
        @Json(name = "message") val message: String,
    ): NewsErrorResponse2()
}
