package com.pmartinb.clickbaitresolver.newslist.domain.model

data class News(
    val headline: String,
    val description: String,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val text: String? = null
)