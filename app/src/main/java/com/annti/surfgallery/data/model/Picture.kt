package com.annti.surfgallery.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "picture")
data class Picture (
    @Json(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "photoUrl")
    val photoUrl: String,
    @Json(name = "publicationDate")
    val publicationDate: Long,
    var isFavorite: Boolean = false
)
