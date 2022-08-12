package com.annti.surfgallery.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.annti.surfgallery.data.model.Picture

@Database(
    entities = [
        Picture::class
    ], version = GalleryDatabase.DB_VERSION
)
abstract class GalleryDatabase : RoomDatabase() {

    abstract fun galleryDao(): GalleryDao

    companion object {
        const val DB_VERSION = 1
        const val DB_MAME = "gallery_database"
    }
}