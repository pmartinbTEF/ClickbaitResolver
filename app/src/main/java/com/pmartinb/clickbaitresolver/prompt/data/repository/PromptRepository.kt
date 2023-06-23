package com.pmartinb.clickbaitresolver.prompt.data.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.pmartinb.clickbaitresolver.commons.storage.DataStoreDataSource
import javax.inject.Inject


class PromptRepository @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
) {

    suspend fun getSummarizerModel() = dataStoreDataSource.getStringValue(SUMMARIZER_MODEL)

    suspend fun saveSummarizerModel(apiKey: String) = dataStoreDataSource.saveStringValue(SUMMARIZER_MODEL, apiKey)

    companion object {
        val SUMMARIZER_MODEL = stringPreferencesKey("summarizer_model")
    }

}