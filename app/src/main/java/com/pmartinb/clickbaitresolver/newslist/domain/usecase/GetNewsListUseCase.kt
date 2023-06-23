package com.pmartinb.clickbaitresolver.newslist.domain.usecase

import com.pmartinb.clickbaitresolver.newslist.data.api.NewsApiClient
import com.pmartinb.clickbaitresolver.newslist.domain.model.News
import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.prompt.data.dto.response.ErrorContent
import com.pmartinb.clickbaitresolver.prompt.extensions.ErrorType
import com.pmartinb.clickbaitresolver.prompt.extensions.getErrorResponse
import com.pmartinb.clickbaitresolver.settings.domain.GetNewsKeyUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor(
    val getNewsKeyUseCase: GetNewsKeyUseCase,
) {
    suspend operator fun invoke(topic: String): Result<List<News>> = withContext(Dispatchers.IO) {
        try {
            var keyResult = runBlocking { getNewsKeyUseCase().first() }
            if (keyResult is Result.Success) {
                performGetNewsCall(topic, keyResult.value)
            } else {
                Result.GenericError(errorContent = ErrorContent.SimpleErrorContent("Error retrieving key"))
            }
        } catch (throwable: Throwable) {
            throwable.getErrorResponse(ErrorType.NewsError)
        }
    }

    private suspend fun performGetNewsCall(
        topic: String,
        key: String
    ): Result.Success<List<News>> {

        val yesterday = Date(Date().time - (1000 * 60 * 60 * 24))
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val newsFrom = dateFormatter.format(yesterday)
        val params = mapOf(
            "q" to topic,
            "from" to newsFrom,
            "sortBy" to "popularity",
            "apiKey" to key
        )
        val result = NewsApiClient.newsService.getNewsList(params)
        val newsList = result.articles.map {
            News(
                headline = it.title,
                description = it.description ?: "",
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                text = it.content
            )
        }.take(10)
        return Result.Success(newsList)
    }
}