package com.pmartinb.clickbaitresolver.newslist.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsListDto(
    @Json(name = "status") val status: String,
    @Json(name = "totalResults") val totalResults: Int,
    @Json(name = "articles") val articles: List<ArticleDto>,
)

@JsonClass(generateAdapter = true)
data class ArticleDto(
    @Json(name = "source") val source: SourceDto,
    @Json(name = "author") val author: String? = null,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String? = null,
    @Json(name = "url") val url: String,
    @Json(name = "urlToImage") val urlToImage: String? = null,
    @Json(name = "publishedAt") val publishedAt: String,
    @Json(name = "content") val content: String? = null,
)

@JsonClass(generateAdapter = true)
data class SourceDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String,
)