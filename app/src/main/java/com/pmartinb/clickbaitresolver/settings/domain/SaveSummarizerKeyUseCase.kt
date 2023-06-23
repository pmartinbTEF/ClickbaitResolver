package com.pmartinb.clickbaitresolver.settings.domain

import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.settings.data.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveSummarizerKeyUseCase @Inject constructor(
    val repository: SettingsRepository
) {
    suspend operator fun invoke(apiKey: String): Flow<Result<Boolean>> {

        return withContext(Dispatchers.IO) {
            repository.saveSummarizerApiKey(apiKey).map { Result.Success(true) }
        }
    }
}