package com.hammer.talkbbokki.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hammer.talkbbokki.data.local.PreferenceKeys.SHOW_ON_BOARDING
import com.hammer.talkbbokki.data.local.PreferenceKeys.VIEW_COUNT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val STORE_NAME = "talkbbokki"
private val Context.dataStore by preferencesDataStore(STORE_NAME)

object PreferenceKeys {
    val SHOW_ON_BOARDING = booleanPreferencesKey("onBoarding")
    val VIEW_COUNT = intPreferencesKey("viewCnt")
}

class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    val viewCnt: Flow<Int> = settingsDataStore.data.map { preferences ->
        preferences[VIEW_COUNT] ?: 0
    }

    suspend fun setViewCnt(isReset: Boolean = false) {
        settingsDataStore.edit { talkbbokki ->
            talkbbokki[VIEW_COUNT] = if (isReset) 0 else (talkbbokki[VIEW_COUNT] ?: 0) + 1
        }
    }

    val showOnBoarding: Flow<Boolean> = settingsDataStore.data.map { it[SHOW_ON_BOARDING] ?: true }
    suspend fun updateShowOnBoarding() {
        settingsDataStore.edit { pref ->
            pref[SHOW_ON_BOARDING] = false
        }
    }
}
