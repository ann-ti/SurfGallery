package com.annti.surfgallery.data.db

import androidx.room.*
import com.annti.surfgallery.data.model.Picture
import kotlinx.coroutines.flow.Flow


@Dao
interface GalleryDao {

    @Query("SELECT * FROM picture")
    fun getFavorites(): Flow<List<Picture>>

    @Query("SELECT * FROM picture WHERE id=:pictureId")
    suspend fun getPicture(pictureId: String): Picture

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePicture(picture: Picture)

    @Delete
    suspend fun removePicture(picture: Picture)

    @Update
    suspend fun updatePicture(picture: Picture)

    @Update
    suspend fun updatePictureList(picture: List<Picture>)
}