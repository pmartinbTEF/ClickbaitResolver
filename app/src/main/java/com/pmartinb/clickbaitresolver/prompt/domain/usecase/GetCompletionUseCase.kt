package com.pmartinb.clickbaitresolver.prompt.domain.usecase


import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.prompt.data.api.ApiClient
import com.pmartinb.clickbaitresolver.prompt.data.dto.request.SummarizerRequest
import com.pmartinb.clickbaitresolver.prompt.extensions.ErrorType
import com.pmartinb.clickbaitresolver.prompt.extensions.getErrorResponse
import com.pmartinb.clickbaitresolver.settings.domain.GetSummarizerKeyUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCompletionUseCase @Inject constructor(
    val getSummarizerKeyUseCase: GetSummarizerKeyUseCase,
    val getSummarizerModel: GetSummarizerModelUseCase,
) {

    suspend operator fun invoke(prompt: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val summarizerKey = getSummarizerKeyUseCase().firstOrNull()?.getOrNull() ?: ""
            val summarizerModel = getSummarizerModel().firstOrNull()?.getOrNull() ?: ""
            val result = ApiClient.getClient(summarizerKey).getCompletion(
                SummarizerRequest(
                    model = summarizerModel,
                    prompt = prompt,
                    max_tokens = 7,
                    temperature = 0
                )
            )
            val responseText = result.choices.firstOrNull()?.text ?: ""
            Result.Success(responseText)
        } catch (throwable: Throwable) {
            throwable.getErrorResponse(ErrorType.GptError)
            //getErrorResult(throwable)
        }
    }

    /*private fun getErrorResult(throwable: Throwable) = when (throwable) {
        is IOException -> NetworkError
        is HttpException -> {
            val code = throwable.code()
            val errorResponse = extractExceptionMessage(throwable)
            GenericError(code, errorResponse)
        }
        else -> {
            throwable.printStackTrace()
            GenericError(null, null)
        }
    }

    fun extractExceptionMessage(exception: HttpException): ErrorResponse.GptErrorResponse? {
        val adapter: JsonAdapter<ErrorResponse.GptErrorResponse> =
            Moshi.Builder().build().adapter(ErrorResponse.GptErrorResponse::class.java).lenient()
        exception.response()?.run {
            errorBody()?.let {
                val errorJson = it.string()
                adapter.fromJson(errorJson)
            }
        }
        return null
    }*/
}

