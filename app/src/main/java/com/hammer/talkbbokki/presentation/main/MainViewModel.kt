package com.hammer.talkbbokki.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.usecase.CategoryLevelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

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
}
