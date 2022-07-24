package com.annti.surfgallery.data.repository

import com.annti.surfgallery.data.mapper.mapToDomain
import com.annti.surfgallery.data.model.TokenResponse
import com.annti.surfgallery.data.network.AuthApi
import com.annti.surfgallery.data.network.AuthRequest
import com.annti.surfgallery.utils.AppError
import com.annti.surfgallery.utils.Request
import com.annti.surfgallery.utils.RequestUtils.requestFlow
import com.annti.surfgallery.utils.handleNetworkErrors
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException


interface AuthRepository {
    suspend fun login(login: String, password: String): Flow<Request<TokenResponse>>
    suspend fun logout()
}

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(login: String, password: String): Flow<Request<TokenResponse>>{
        return requestFlow{
            val authResponse = authApi.login(AuthRequest(login, password))
            val user = authResponse.mapToDomain()
            user
        }
    }

    override suspend fun logout() {
        try {
            authApi.logout()
        } catch (e: HttpException){
            when (e.code()){
                401 -> throw AppError(
                    AppError.Code.INVALID_LOGIN_OR_PASSWORD,
                    "Необходимо авторизоваться заново"
                )
                else -> throw e
            }
        }
        catch (e: Throwable){
            e.message
        }
    }

}