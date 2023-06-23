package com.pmartinb.clickbaitresolver.prompt.data.dto.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SummarizerRequest(
    @Json(name = "model") val model: String,
    @Json(name = "prompt") val prompt: String,
    @Json(name = "max_tokens") val max_tokens: Int = 7,
    @Json(name = "temperature") val temperature: Int = 0,
    @Json(name = "top_p") val top_p: Int = 1,
    @Json(name = "n") val n: Int = 1,
    @Json(name = "stream") val stream: Boolean = false,
    @Json(name = "logprobs") val logprobs: String? = null,
    @Json(name = "stop") val stop: String = "\n",
)
