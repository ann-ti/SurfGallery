package com.annti.surfgallery.data.repository

import com.annti.surfgallery.data.db.Database
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.data.network.GalleryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface GalleryRepository {

    suspend fun getPicture(): List<Picture>
    fun getFavorites(): Flow<List<Picture>>
    suspend fun getPicture(pictureId: String): Picture
    suspend fun savePicture(picture: Picture)
    suspend fun removePicture(picture: Picture)
    suspend fun updatePicture(picture: Picture)
    suspend fun updatePictureList(picture: List<Picture>)
}

class GalleryRepositoryImpl(
    private val galleryApi: GalleryApi
) : GalleryRepository {

    private val favPictureDao = Database.instance.favPictureDao()

    override suspend fun getPicture(): List<Picture> =
        galleryApi.getPicture()

    override fun getFavorites(): Flow<List<Picture>> =
        favPictureDao.getFavorites()


    override suspend fun savePicture(picture: Picture) {
        withContext(Dispatchers.IO) {
            favPictureDao.savePicture(picture)
        }
    }

    override suspend fun getPicture(pictureId: String): Picture =
        withContext(Dispatchers.IO) {
            favPictureDao.getPicture(pictureId)
        }

    override suspend fun updatePicture(picture: Picture) {
        withContext(Dispatchers.IO) {
            favPictureDao.updatePicture(picture)
        }
    }

    override suspend fun updatePictureList(picture: List<Picture>) {
        withContext(Dispatchers.IO) {
            favPictureDao.updatePictureList(picture)
        }
    }

    override suspend fun removePicture(picture: Picture) {
        withContext(Dispatchers.IO) {
            favPictureDao.removePicture(picture)
        }
    }
}