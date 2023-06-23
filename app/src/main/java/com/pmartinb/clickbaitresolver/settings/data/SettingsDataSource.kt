package com.pmartinb.clickbaitresolver.settings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
        by preferencesDataStore(name = "global")

@Singleton
open class SettingsDataSource @Inject constructor(
    @ApplicationContext context: Context
) : DataStore<androidx.datastore.preferences.core.Preferences> by context.dataStore {

}