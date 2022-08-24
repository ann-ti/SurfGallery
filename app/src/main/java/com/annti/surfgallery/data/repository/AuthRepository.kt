package com.annti.surfgallery.data.repository

import com.annti.surfgallery.data.db.Database
import com.annti.surfgallery.data.mapper.mapToDomain
import com.annti.surfgallery.data.model.TokenResponse
import com.annti.surfgallery.data.network.AuthApi
import com.annti.surfgallery.data.network.AuthRequest
import com.annti.surfgallery.utils.AppError
import com.annti.surfgallery.utils.Request
import com.annti.surfgallery.utils.RequestUtils.requestFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

interface AuthRepository {
    suspend fun login(login: String, password: String): Flow<Request<TokenResponse>>
    suspend fun logout()
}

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    private val galleryDao = Database.instance.galleryDao()

    override suspend fun login(login: String, password: String): Flow<Request<TokenResponse>> {
        return requestFlow {
            val authResponse = authApi.login(AuthRequest(login, password))
            val user = authResponse.mapToDomain()
            user
        }
    }

    //TODO fix logout: delete all info
    override suspend fun logout() {
        try {
            authApi.logout()
            galleryDao.clearAll()
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> throw AppError(
                    AppError.Code.INVALID_LOGIN_OR_PASSWORD,
                    "Необходимо авторизоваться заново"
                )
                else -> throw e
            }
        } catch (e: Throwable) {
            e.message
        }
    }

}