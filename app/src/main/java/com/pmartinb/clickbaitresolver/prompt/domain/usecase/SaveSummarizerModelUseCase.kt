package com.pmartinb.clickbaitresolver.prompt.domain.usecase

import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.prompt.data.repository.PromptRepository
import com.pmartinb.clickbaitresolver.settings.data.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveSummarizerModelUseCase @Inject constructor(
    val repository: PromptRepository
) {
    suspend operator fun invoke(model: String): Flow<Result<Boolean>> {

        return withContext(Dispatchers.IO) {
            repository.saveSummarizerModel(model).map { Result.Success(true) }
        }
    }
}