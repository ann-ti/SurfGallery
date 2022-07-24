package com.annti.surfgallery.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annti.surfgallery.data.model.UserInfo
import com.annti.surfgallery.domain.AuthUseCase
import com.annti.surfgallery.utils.LoadState
import com.annti.surfgallery.utils.Request
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    val loginErrorLiveData = MutableLiveData<LoginError>()
    val passwordErrorLiveData = MutableLiveData<PasswordError>()


    val loadState = MutableLiveData<LoadState>()
    val errorLiveData = LiveEvent<String>()
    val error: LiveEvent<String>
        get() = errorLiveData

    private val inputValidator by lazy {
        InputValidator(loginErrorLiveData, passwordErrorLiveData)
    }

    private var password: String = ""
    private var login: String = ""

    fun loginAuth() {
        if (inputValidator.isFieldValid(login, password)) {
            viewModelScope.launch(Dispatchers.IO) {
                authUseCase.login("+7$login", password).collect { requestState ->
                    when (requestState) {
                        is Request.Loading -> {
                            loadState.postValue(LoadState.LOADING)
                        }
                        is Request.Success -> {
                            loadState.postValue(LoadState.SUCCESS)
                        }
                        is Request.Error -> {
                            loadState.postValue(LoadState.ERROR)
                        }
                    }
                }
            }
        }
    }

    fun setLogin(login: String) {
        this.login = login
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authUseCase.logout()
            } catch (e: Throwable) {
                errorLiveData.postValue(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    private val userInfoLiveData = MutableLiveData<UserInfo?>()
    val userInfo: LiveData<UserInfo?>
        get() = userInfoLiveData

    fun getUserInfo() {
        viewModelScope.launch {
            try {
                val result = authUseCase.getUserInfo()
                userInfoLiveData.postValue(result)
            } catch (e: Throwable) {

            }
        }
    }
}