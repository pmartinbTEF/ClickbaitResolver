package com.pmartinb.clickbaitresolver.settings.domain

import com.pmartinb.clickbaitresolver.prompt.data.Result
import com.pmartinb.clickbaitresolver.settings.data.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetKeysUseCase @Inject constructor(
    val repository: SettingsRepository,
    val getNewsKeyUseCase: GetNewsKeyUseCase,
    val getSummarizerKeyUseCase: GetSummarizerKeyUseCase,
) {
    suspend operator fun invoke(): Pair<Flow<Result<String>>, Flow<Result<String>>> {
        return Pair(getNewsKeyUseCase(),getSummarizerKeyUseCase())
    }
}