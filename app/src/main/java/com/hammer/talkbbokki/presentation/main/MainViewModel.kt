package com.hammer.talkbbokki.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.talkbbokki.R
import com.hammer.talkbbokki.data.local.DataStoreManager
import com.hammer.talkbbokki.data.local.cache.UserInfoCache
import com.hammer.talkbbokki.domain.model.CategoryLevel
import com.hammer.talkbbokki.domain.model.UserInfoRequest
import com.hammer.talkbbokki.domain.repository.UserInfoRepository
import com.hammer.talkbbokki.domain.usecase.CategoryLevelUseCase
import com.hammer.talkbbokki.ui.util.validateNickname
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class MainViewModel @Inject constructor(
    useCase: CategoryLevelUseCase,
    private val userInfoRepository: UserInfoRepository,
    private val dataStoreManager: DataStoreManager,
    private val cache: UserInfoCache
) : ViewModel() {
    val categoryLevel: StateFlow<List<CategoryLevel>> = useCase.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _userNickname: MutableStateFlow<String> = MutableStateFlow("")
    val userNickname: StateFlow<String> get() = _userNickname.asStateFlow()

    private val _showNicknameDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showNicknameDialog: StateFlow<Boolean> get() = _showNicknameDialog.asStateFlow()

    private val _forceSettingNickname: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val forceSettingNickname: StateFlow<Boolean> get() = _forceSettingNickname.asStateFlow()

    private val _verifyMessage: MutableStateFlow<Int> = MutableStateFlow(-1)
    val verifyMessage: StateFlow<Int> get() = _verifyMessage.asStateFlow()

    init {
        getUserNickname()
    }

    private fun getUserNickname() {
        viewModelScope.launch {
            delay(1_000)
            userInfoRepository.getUserNickname()
                .catch {
                    _forceSettingNickname.update { true }
                }
                .collect { nickname ->
                    _forceSettingNickname.update { nickname.isNullOrBlank() }
                    if (!nickname.isNullOrBlank()) {
                        _userNickname.value = nickname
                    }
                }
        }
    }

    fun checkNickname(nickname: String) {
        when {
            nickname.length < 2 -> _verifyMessage.value = R.string.setting_nickname_error_too_short
            nickname.length > textLimitCount ->
                _verifyMessage.value =
                    R.string.setting_nickname_error_too_long
            !nickname.validateNickname() ->
                _verifyMessage.value =
                    R.string.setting_nickname_error_not_allow_char
            else -> {
                isNicknameExist(nickname)
            }
        }
    }

    private fun isNicknameExist(nickname: String) {
        viewModelScope.launch {
            userInfoRepository.checkUserNickname(nickname)
                .catch {
                    if (it is HttpException) {
                        when (it.code()) {
                            // 중복돤 닉네임
                            409 -> _verifyMessage.value = R.string.setting_nickname_error_exist
                            else -> {}
                        }
                    }
                }.collect {
                    _verifyMessage.value = R.string.setting_nickname_usable
                }
        }
    }

    fun saveUserNickname(nickname: String) {
        viewModelScope.launch {
            userInfoRepository.postUserNickname(
                UserInfoRequest(
                    uuid = cache.id,
                    pushToken = cache.deviceToken,
                    nickName = nickname.trim()
                )
            )
                .catch { }
                .collect {
                    cache.nickname = nickname
                    _showNicknameDialog.update { false }
                    _forceSettingNickname.update { false }
                    _userNickname.value = nickname
                }
        }
    }

    fun openNicknamePage() {
        _showNicknameDialog.update { true }
        checkNickname("")
    }

    fun closeNicknamePage() {
        _showNicknameDialog.update { false }
    }

    companion object {
        const val textLimitCount = 9
    }
}
