package com.annti.surfgallery.data.network

import com.annti.surfgallery.data.model.TokenResponse
import com.squareup.moshi.JsonClass
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
data class AuthRequest(
    val phone: String,
    val password: String
)

interface AuthApi {

    @POST("api/auth/login")
    suspend fun login(
        @Body authRequest: AuthRequest
    ): TokenResponse

    //TODO fix logout
    @POST("api/auth/logout")
    suspend fun logout()
}