package com.hammer.talkbbokki.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val repo: UserInfoRepository
) : ViewModel() {
    private val todayDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    init {
        viewModelScope.launch {
            dataStoreManager.appVisitDate
                .map {
                    if (it != todayDate) {
                        dataStoreManager.updateAppVisitDate(todayDate)
                    }
                }
                .collect()
        }
    }

    fun saveDeviceToken(id: String, newToken: String) {
        viewModelScope.launch {
            repo.getUserDeviceToken().collect { savedToken ->
                if (newToken != savedToken) {
                    repo.postUserInfo(id, newToken)
                        .catch { }
                        .collect {
                            Log.d("pushToken", "save pushToken success : $newToken")
                        }
                }
            }
        }
    }
}
