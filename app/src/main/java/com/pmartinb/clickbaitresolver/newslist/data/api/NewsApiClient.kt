package com.pmartinb.clickbaitresolver.newslist.data.api

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object NewsApiClient {

    private const val BASE_URL = "https://newsapi.org/"

    val moshi: Moshi = Moshi.Builder().build()

    val newsService = Retrofit.Builder()
        .baseUrl(BASE_URL)

        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(Interceptor {
                    it.proceed(
                        it.request().newBuilder()
                            .build()
                    )
                })
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create<NewsApiServices>()

}
