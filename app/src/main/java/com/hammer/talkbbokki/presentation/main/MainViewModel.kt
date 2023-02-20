package com.hammer.talkbbokki.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.usecase.CategoryLevelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    useCase: CategoryLevelUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val categoryLevel: StateFlow<List<CategoryLevel>> = useCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val todayDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    init {
        updateVisitDate()
    }

    private fun updateVisitDate() {
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
}
