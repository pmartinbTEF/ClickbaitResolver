package com.pmartinb.clickbaitresolver.prompt.domain.usecase

import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.prompt.data.repository.PromptRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSummarizerModelUseCase @Inject constructor(
    val repository: PromptRepository
) {
    suspend operator fun invoke(): Flow<Result<String>> {

        return withContext(Dispatchers.IO) {
            repository.getSummarizerModel().map { Result.Success(it) }
        }
    }
}