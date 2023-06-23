package com.pmartinb.clickbaitresolver.prompt.data.dto.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GptCompletionDto(
    @Json(name = "id") val id: String,
    @Json(name = "object") val objt: String,
    @Json(name = "created") val created: Long,
    @Json(name = "model") val model: String,
    @Json(name = "choices") val choices: List<GptChoicesDto>,
    @Json(name = "usage") val usage: GptUsageDto,

    )

@JsonClass(generateAdapter = true)
data class GptChoicesDto(
    @Json(name = "text") val text: String,
    @Json(name = "index") val index: Int,
    @Json(name = "logprobs") val logprobs: String? = null,
    @Json(name = "finish_reason") val finish_reason: String?,
)

@JsonClass(generateAdapter = true)
data class GptUsageDto(
    @Json(name = "prompt_tokens") val prompt_tokens: Int,
    @Json(name = "completion_tokens") val completion_tokens: Int,
    @Json(name = "total_tokens") val total_tokens: Int,
)
