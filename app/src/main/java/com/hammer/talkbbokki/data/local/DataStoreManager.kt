package com.hammer.talkbbokki.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hammer.talkbbokki.data.local.PreferenceKeys.APP_VISIT_DATE
import com.hammer.talkbbokki.data.local.PreferenceKeys.BOOKMARK_CANCEL_DIALOG
import com.hammer.talkbbokki.data.local.PreferenceKeys.DEVICE_TOKEN
import com.hammer.talkbbokki.data.local.PreferenceKeys.SHOW_ON_BOARDING
import com.hammer.talkbbokki.data.local.PreferenceKeys.USER_NICKNAME
import com.hammer.talkbbokki.data.local.PreferenceKeys.VIEW_CARD_LIST
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val STORE_NAME = "talkbbokki"
private val Context.dataStore by preferencesDataStore(STORE_NAME)

object PreferenceKeys {
    val SHOW_ON_BOARDING = booleanPreferencesKey("onBoarding")
    val BOOKMARK_CANCEL_DIALOG = intPreferencesKey("bookmark_cancel_dialog")
    val VIEW_CARD_LIST = stringSetPreferencesKey("viewCard")
    val APP_VISIT_DATE = intPreferencesKey("visitDate")
    val DEVICE_TOKEN = stringPreferencesKey("device_token")
    val USER_NICKNAME = stringPreferencesKey("nickname")
}

class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    val appDeviceToken: Flow<String> = settingsDataStore.data.map { pref ->
        pref[DEVICE_TOKEN] ?: ""
    }

    suspend fun updateDeviceToken(token: String) {
        settingsDataStore.edit { pref ->
            pref[DEVICE_TOKEN] = token
        }
    }

    val userNickname: Flow<String?> = settingsDataStore.data.map { pref ->
        pref[USER_NICKNAME]
    }

    suspend fun updateNickname(nickname: String) {
        settingsDataStore.edit { pref ->
            pref[USER_NICKNAME] = nickname
        }
    }

    val viewCnt: Flow<Int> = settingsDataStore.data.map { preferences ->
        val viewCards = preferences[VIEW_CARD_LIST] ?: emptySet()
        viewCards.count()
    }

    val appVisitDate: Flow<Int> = settingsDataStore.data.map { preferences ->
        preferences[APP_VISIT_DATE] ?: 0
    }

    suspend fun updateAppVisitDate(date: Int) {
        settingsDataStore.edit { preferences ->
            preferences[APP_VISIT_DATE] = date
        }
        resetViewCards()
    }

    val viewCards: Flow<ViewCardPrefData> = settingsDataStore.data.map { pref ->
        val viewCards = pref[VIEW_CARD_LIST] ?: emptySet()
        ViewCardPrefData(viewCards.map { it.toInt() }.toList())
    }

    suspend fun updateViewCards(id: Int) {
        settingsDataStore.edit { pref ->
            val viewCards = pref[VIEW_CARD_LIST] ?: emptySet()
            pref[VIEW_CARD_LIST] = viewCards.toMutableSet().apply {
                add(id.toString())
            }
        }
    }

    private suspend fun resetViewCards() {
        settingsDataStore.edit { pref ->
            pref[VIEW_CARD_LIST] = emptySet()
        }
    }

    val showOnBoarding: Flow<Boolean> = settingsDataStore.data.map { it[SHOW_ON_BOARDING] ?: true }
    suspend fun updateShowOnBoarding() {
        settingsDataStore.edit { pref ->
            pref[SHOW_ON_BOARDING] = false
        }
    }

    val bookmarkCancelDialogDate: Flow<Int> = settingsDataStore.data.map {
        it[BOOKMARK_CANCEL_DIALOG] ?: 0
    }

    suspend fun updateBookmarkCancelDialogDate(date: Int) {
        settingsDataStore.edit { pref ->
            pref[BOOKMARK_CANCEL_DIALOG] = date
        }
    }
}

data class ViewCardPrefData(
    val viewCards: List<Int>
)
