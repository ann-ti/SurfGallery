package com.annti.surfgallery.data.network

import com.annti.surfgallery.data.model.Picture
import retrofit2.http.GET

interface GalleryApi {

    @AuthRequired
    @GET("api/picture")
    suspend fun getPicture(): List<Picture>
}

