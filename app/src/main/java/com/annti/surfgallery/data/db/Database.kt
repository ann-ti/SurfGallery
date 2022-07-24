package com.annti.surfgallery.data.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: GalleryDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            GalleryDatabase::class.java,
            GalleryDatabase.DB_MAME
        )
            .build()
    }
}