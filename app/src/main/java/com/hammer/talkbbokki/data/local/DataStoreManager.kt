package com.hammer.talkbbokki.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val STORE_NAME = "talkbbokki"
private val Context.dataStore by preferencesDataStore(STORE_NAME)

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore
    private val key = intPreferencesKey("viewCnt")

    suspend fun setViewCnt(isReset: Boolean = false) {
        settingsDataStore.edit { talkbbokki ->
            talkbbokki[key] = if (isReset) 0 else (talkbbokki[key] ?: 0) + 1
        }
    }

    val viewCnt: Flow<Int> = settingsDataStore.data.map { preferences ->
        preferences[key] ?: 0
    }
}
