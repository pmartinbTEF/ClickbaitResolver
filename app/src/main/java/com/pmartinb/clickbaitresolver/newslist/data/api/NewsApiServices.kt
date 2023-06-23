package com.pmartinb.clickbaitresolver.newslist.data.api

import com.pmartinb.clickbaitresolver.newslist.data.dto.NewsListDto
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApiServices {

    @GET("/v2/everything")
    suspend fun getNewsList(@QueryMap params: Map<String, String> ): NewsListDto

}
