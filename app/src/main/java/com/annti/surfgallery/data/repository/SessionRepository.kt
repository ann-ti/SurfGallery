package com.annti.surfgallery.data.repository

import com.annti.surfgallery.data.model.UserInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import java.net.UnknownServiceException
import java.time.Instant

@JsonClass(generateAdapter = true)
data class AuthSession(
    val token: String,
    val userInfo: UserInfo
)

interface SessionRepository {
    fun saveSession(authSession: AuthSession?)
    fun getSession(): AuthSession?
}

class SessionRepositoryImpl(
    private val preferencesRepository: PreferencesRepository,
    moshi: Moshi
) : SessionRepository {
    private companion object {
        const val AUTH_SESSION = "AUTH_SESSION"
    }

    @OptIn(ExperimentalStdlibApi::class)
    private val serializationAdapter = moshi.adapter<AuthSession>()

    override fun saveSession(authSession: AuthSession?) {
        if (authSession != null)
            preferencesRepository.putString(AUTH_SESSION, serializationAdapter.toJson(authSession))
        else
            preferencesRepository.remove(AUTH_SESSION)
    }

    override fun getSession(): AuthSession? =
        preferencesRepository.getStringOrNull(AUTH_SESSION)?.let {
            serializationAdapter.fromJson(it)
        }
}