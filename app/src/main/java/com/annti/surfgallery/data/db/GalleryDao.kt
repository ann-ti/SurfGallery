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

    @Query("SELECT * FROM picture WHERE title LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<Picture>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePicture(picture: Picture)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePictureList(pictureList: List<Picture>)

    @Delete
    suspend fun removePicture(picture: Picture)

    @Query("DELETE FROM picture")
    suspend fun clearAll()

    @Update
    suspend fun updatePicture(picture: Picture)

    @Update
    suspend fun updatePictureList(pictureList: List<Picture>)
}