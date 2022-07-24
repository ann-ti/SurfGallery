package com.annti.surfgallery.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponse(
    @Json(name = "token")
    val token: String,
    @Json(name = "user_info")
    val userInfo: UserInfo
)

@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "id")
    val id: String,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "firstName")
    val firstName: String,
    @Json(name = "lastName")
    val lastName: String,
    @Json(name = "avatar")
    val avatar: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "about")
    val about: String
)