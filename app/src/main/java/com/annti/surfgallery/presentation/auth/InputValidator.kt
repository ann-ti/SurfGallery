package com.annti.surfgallery.presentation.auth

import androidx.lifecycle.MutableLiveData

class InputValidator(
    private val loginErrorLiveData: MutableLiveData<LoginError>,
    private val passwordErrorLiveData: MutableLiveData<PasswordError>
) {

    fun isFieldValid(login: String, password: String): Boolean {
        val isLoginValid = when {
            login.isEmpty() -> {
                loginErrorLiveData.value = LoginError.EMPTY
                false
            }
            login.length != 10 -> {
                loginErrorLiveData.value = LoginError.NOT_VALID
                false
            }
            else -> {
                loginErrorLiveData.value = LoginError.VALID
                true
            }
        }
        val isPasswordValid = when {
            password.isEmpty() -> {
                passwordErrorLiveData.value = PasswordError.EMPTY
                false
            }
            password.length !in 6..255 -> {
                passwordErrorLiveData.value = PasswordError.NOT_VALID
                false
            }
            else -> {
                passwordErrorLiveData.value = PasswordError.VALID
                true
            }
        }
        return isLoginValid && isPasswordValid
    }


}

enum class LoginError {
    EMPTY,
    NOT_VALID,
    VALID
}

enum class PasswordError {
    EMPTY,
    NOT_VALID,
    VALID
}