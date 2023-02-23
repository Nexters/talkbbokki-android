package com.hammer.talkbbokki.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hammer.talkbbokki.data.local.PreferenceKeys.APP_VISIT_DATE
import com.hammer.talkbbokki.data.local.PreferenceKeys.BOOKMARK_CANCEL_DIALOG
import com.hammer.talkbbokki.data.local.PreferenceKeys.SHOW_ON_BOARDING
import com.hammer.talkbbokki.data.local.PreferenceKeys.VIEW_CARD_LIST
import com.hammer.talkbbokki.data.local.PreferenceKeys.VIEW_COUNT
import com.hammer.talkbbokki.data.local.PreferenceKeys.VIEW_INDEX
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val STORE_NAME = "talkbbokki"
private val Context.dataStore by preferencesDataStore(STORE_NAME)

object PreferenceKeys {
    val SHOW_ON_BOARDING = booleanPreferencesKey("onBoarding")
    val BOOKMARK_CANCEL_DIALOG = intPreferencesKey("bookmark_cancel_dialog")
    val VIEW_COUNT = intPreferencesKey("viewCnt")
    val VIEW_INDEX = stringSetPreferencesKey("viewIndex")
    val VIEW_CARD_LIST = stringSetPreferencesKey("viewCard")
    val APP_VISIT_DATE = intPreferencesKey("visitDate")
}

class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val settingsDataStore = appContext.dataStore

    val viewCnt: Flow<Int> = settingsDataStore.data.map { preferences ->
        val viewCards = preferences[VIEW_CARD_LIST] ?: emptySet()
        viewCards.count()
    }

    suspend fun setViewCnt(isReset: Boolean = false) {
        settingsDataStore.edit { talkbbokki ->
            talkbbokki[VIEW_COUNT] = if (isReset) 0 else (talkbbokki[VIEW_COUNT] ?: 0) + 1
        }
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

    suspend fun resetViewCards() {
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

    suspend fun setOpenedIndex(isReset: Boolean = false, index: String) {
        settingsDataStore.edit { talkbbokki ->
            talkbbokki[VIEW_INDEX] =
                if (isReset) setOf() else (talkbbokki[VIEW_INDEX] ?: setOf()).plus(index)
        }
    }

    val openedIndex: Flow<Set<String>> = settingsDataStore.data.map { preferences ->
        preferences[VIEW_INDEX] ?: setOf()
    }
}

data class ViewCardPrefData(
    val viewCards: List<Int>
)
