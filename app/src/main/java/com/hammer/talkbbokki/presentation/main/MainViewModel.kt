package com.hammer.talkbbokki.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.repository.UserInfoRepository
import com.hammer.talkbbokki.domain.usecase.CategoryLevelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class MainViewModel @Inject constructor(
    useCase: CategoryLevelUseCase,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {
    val categoryLevel: StateFlow<List<CategoryLevel>> = useCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val showNicknameDialog: StateFlow<Boolean> =
        userInfoRepository.getUserNickname().map { nickname ->
            nickname == null
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    private val _errorMessage: MutableStateFlow<Int> = MutableStateFlow(-1)
    val errorMessage: StateFlow<Int> get() = _errorMessage.asStateFlow()

    fun isNicknameExist(nickname: String) {
        viewModelScope.launch {
            userInfoRepository.checkUserNickname(nickname)
                .catch {
                    if (it is HttpException) {
                        when (it.code()) {
                            404 -> _errorMessage.value = -1
                            else -> {}
                        }
                    }
                }.collect {
                }
        }
    }

    fun saveUserNickname(nickname: String) {
        viewModelScope.launch {
            userInfoRepository.postUserNickname(nickname)
                .catch { }
                .collect {
                }
        }
    }
}
