package com.annti.surfgallery.domain

import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.data.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow

interface GalleryUseCase {
    suspend fun getPicture(): List<Picture>
    fun getPictureDb(): Flow<List<Picture>>
    suspend fun getPictureId(pictureId: String): Picture
    suspend fun savePicture(picture: Picture)
    suspend fun removePicture(picture: Picture)
    suspend fun updatePicture(picture: Picture)
    suspend fun search(query: String): List<Picture>
}

class GalleryUseCaseImpl(
    private val galleryRepository: GalleryRepository
): GalleryUseCase {

    override suspend fun getPicture(): List<Picture> =
        galleryRepository.getPicture()

    override fun getPictureDb(): Flow<List<Picture>> =
        galleryRepository.getPictureDb()

    override suspend fun getPictureId(pictureId: String): Picture =
        galleryRepository.getPicture(pictureId)

    override suspend fun savePicture(picture: Picture) {
        galleryRepository.savePicture(picture)
    }

    override suspend fun removePicture(picture: Picture) {
        galleryRepository.removePicture(picture)
    }

    override suspend fun updatePicture(picture: Picture) {
        galleryRepository.updatePicture(picture)
    }

    override suspend fun search(query: String): List<Picture> =
        galleryRepository.search(query)

}