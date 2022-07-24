package com.annti.surfgallery.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.annti.surfgallery.data.db.Database
import com.annti.surfgallery.data.model.TokenResponse
import com.annti.surfgallery.data.model.UserInfo
import com.annti.surfgallery.data.repository.AuthRepository
import com.annti.surfgallery.data.repository.AuthSession
import com.annti.surfgallery.data.repository.SessionRepository
import com.annti.surfgallery.utils.AppError
import com.annti.surfgallery.utils.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant

interface AuthUseCase {

    suspend fun login(phone: String, password: String): Flow<Request<TokenResponse>>
    suspend fun logout()
    val isAuthorized: Boolean
    suspend fun getAccessToken(): String?
    fun getUserInfo(): UserInfo?
}

class AuthUseCaseImpl(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository
) : AuthUseCase {

    private var session: AuthSession? = null

    init {
        session = sessionRepository.getSession()
    }

    private fun TokenResponse.toSession(now: Instant) = AuthSession(
        token = token,
        userInfo = userInfo
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun login(phone: String, password: String): Flow<Request<TokenResponse>> {
        val response = authRepository.login(phone, password)
        response.collect {
            if (it is Request.Success) {
                val newSession = it.data.toSession(Instant.now())
                sessionRepository.saveSession(newSession)
                session = newSession
            }
        }
        return authRepository.login(phone, password)
    }

    override suspend fun logout() {
        sessionRepository.saveSession(null)
        session = null
        authRepository.logout()
    }

    override fun getUserInfo(): UserInfo? {
        val currentSession = session ?: return null
        return currentSession.userInfo
    }

    override val isAuthorized: Boolean
        get() = session != null

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAccessToken(): String? {
        val currentSession = session ?: return null
        val now = Instant.now()
        return currentSession.token

    }

}