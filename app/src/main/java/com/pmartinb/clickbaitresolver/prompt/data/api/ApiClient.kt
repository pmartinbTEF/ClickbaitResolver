package com.pmartinb.clickbaitresolver.prompt.data.api

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


object ApiClient {

    var headerBearer: String = "Bearer "

    private const val SUMMARIZER_BASE_URL = "https://api.openai.com/"

    private const val HEADER_AUTHORIZATION = "Authorization"

    val moshi: Moshi = Moshi.Builder().build()

    val serviceSummarizer = Retrofit.Builder()
        .baseUrl(SUMMARIZER_BASE_URL)

        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(Interceptor {
                    it.proceed(
                        it.request().newBuilder()
                            .header(HEADER_AUTHORIZATION, headerBearer)
                            .build()
                    )
                })
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create<ApiServices>()

    fun getClient(authToken: String): ApiServices {
        headerBearer = authToken
        return serviceSummarizer
    }

}
