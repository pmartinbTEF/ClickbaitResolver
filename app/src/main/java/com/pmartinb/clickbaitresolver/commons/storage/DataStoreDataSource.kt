package com.pmartinb.clickbaitresolver.commons.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "global")

@Singleton
class DataStoreDataSource @Inject constructor(
    @ApplicationContext context: Context
) : DataStore<androidx.datastore.preferences.core.Preferences> by context.dataStore {

    suspend fun getStringValue(storageKey: Preferences.Key<String>): Flow<String> {
        delay(1000 + Random().nextInt(2000).toLong())
        return data.map { values ->
            values[storageKey] ?: ""
        }
    }

    suspend fun saveStringValue(storageKey: Preferences.Key<String>, value: String): Flow<String> {
        delay(1000 + Random().nextInt(2000).toLong())
        edit { values ->
            values[storageKey] = value
        }

        return flowOf(true.toString())
    }

}