package com.annti.surfgallery.data.repository

import com.annti.surfgallery.data.db.Database
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.data.network.GalleryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface GalleryRepository {

    suspend fun getPicture(): List<Picture>
    fun getPictureDb(): Flow<List<Picture>>
    suspend fun getPicture(pictureId: String): Picture
    suspend fun savePicture(picture: Picture)
    suspend fun removePicture(picture: Picture)
    suspend fun updatePicture(picture: Picture)
}

class GalleryRepositoryImpl(
    private val galleryApi: GalleryApi
) : GalleryRepository {

    private val galleryDao = Database.instance.galleryDao()

    override suspend fun getPicture(): List<Picture> {
        return try {
            galleryApi.getPicture().apply {
                if (!this.isNullOrEmpty()){
                    withContext(Dispatchers.IO){
                        galleryDao.savePictureList(this@apply)
                        galleryDao.updatePictureList(this@apply)
                    }
                }
            }
        } catch (e: Throwable){
            emptyList()
        }
    }


    override fun getPictureDb(): Flow<List<Picture>> =
        galleryDao.getFavorites()


    override suspend fun savePicture(picture: Picture) {
        withContext(Dispatchers.IO) {
            galleryDao.savePicture(picture)
        }
    }

    override suspend fun getPicture(pictureId: String): Picture =
        withContext(Dispatchers.IO) {
            galleryDao.getPicture(pictureId)
        }

    override suspend fun updatePicture(picture: Picture) {
        withContext(Dispatchers.IO) {
            galleryDao.updatePicture(picture)
        }
    }

    override suspend fun removePicture(picture: Picture) {
        withContext(Dispatchers.IO) {
            galleryDao.removePicture(picture)
        }
    }
}