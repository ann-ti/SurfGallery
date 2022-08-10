package com.annti.surfgallery.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
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
): Parcelable
