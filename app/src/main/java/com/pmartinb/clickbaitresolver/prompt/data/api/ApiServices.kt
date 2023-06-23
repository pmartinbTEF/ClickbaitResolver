package com.pmartinb.clickbaitresolver.prompt.data.api

import com.pmartinb.clickbaitresolver.prompt.data.dto.request.SummarizerRequest
import com.pmartinb.clickbaitresolver.prompt.data.dto.response.GptCompletionDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("/v1/completions")
    suspend fun getCompletion(@Body req: SummarizerRequest): GptCompletionDto


}
