package com.pmartinb.clickbaitresolver.settings.data

import androidx.datastore.preferences.core.stringPreferencesKey
import com.pmartinb.clickbaitresolver.commons.storage.DataStoreDataSource
import javax.inject.Inject


class SettingsRepository @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
) {
    suspend fun getNewsApiKey() = dataStoreDataSource.getStringValue(NEWS_KEY)

    suspend fun saveNewsApiKey(apiKey: String) = dataStoreDataSource.saveStringValue(NEWS_KEY, apiKey)

    suspend fun getSummarizerApiKey() = dataStoreDataSource.getStringValue(SUMMARIZER_KEY)

    suspend fun saveSummarizerApiKey(apiKey: String) = dataStoreDataSource.saveStringValue(SUMMARIZER_KEY, apiKey)

    companion object {
        val NEWS_KEY = stringPreferencesKey("news_key")
        val SUMMARIZER_KEY = stringPreferencesKey("summarizer_key")
    }

}