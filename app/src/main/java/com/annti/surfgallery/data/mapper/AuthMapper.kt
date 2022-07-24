package com.annti.surfgallery.data.mapper

import com.annti.surfgallery.data.model.TokenResponse
import com.annti.surfgallery.data.model.UserInfo
import com.squareup.moshi.Json

fun TokenResponse.mapToDomain(): TokenResponse {
    return TokenResponse(
        this.token,
        UserInfo(
            this.userInfo.id,
            this.userInfo.phone,
            this.userInfo.email,
            this.userInfo.firstName,
            this.userInfo.lastName,
            this.userInfo.avatar,
            this.userInfo.city,
            this.userInfo.about
        )
    )
}