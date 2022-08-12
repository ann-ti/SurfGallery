package com.annti.surfgallery.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.domain.GalleryUseCase
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val galleryUseCase: GalleryUseCase
) : ViewModel() {

    private val galleryListLiveData = MutableLiveData<List<Picture>>()
    private var errorData = LiveEvent<String>()
    private var errorViewData = LiveEvent<Boolean>()

    val error: LiveEvent<String>
        get() = errorData
    val errorView: LiveEvent<Boolean>
        get() = errorViewData
    val galleryList: LiveData<List<Picture>>
        get() = galleryListLiveData

    fun getGalleryList() {
        viewModelScope.launch {
            try {
                galleryUseCase.getPicture()
            } catch (e: Throwable) {
                errorData.postValue("getGalleryList(): $e")
            }
        }
    }

    fun getGalleryListDb(){
            try {
                galleryUseCase.getPictureDb()
                    .onEach { galleryListLiveData.postValue(it) }
                    .launchIn(viewModelScope)
            } catch (e: Throwable) {
                errorData.postValue("getGalleryList(): $e")
            }
    }

    fun saveOrRemovePicture(picture: Picture) {
        viewModelScope.launch {
            try {
                if (picture.isFavorite) {
                    removePicture(picture)
                } else savePicture(picture)
                galleryUseCase.updatePicture(picture)
            } catch (e: Throwable) {
                errorData.postValue("saveOrRemovePicture: $e")
            }
        }
    }

    fun savePicture(picture: Picture) {
        viewModelScope.launch {
            try {
                val favPic = galleryUseCase.getPictureId(picture.id).apply {
                    this.isFavorite = true
                }
                galleryUseCase.updatePicture(favPic)
            } catch (e: Throwable) {
                errorData.postValue("savePicture: $e")
            }
        }
    }

    fun removePicture(picture: Picture) {
        viewModelScope.launch {
            try {
                val favPic = galleryUseCase.getPictureId(picture.id).apply {
                    this.isFavorite = false
                }
                galleryUseCase.updatePicture(favPic)
            } catch (e: Throwable) {
                errorData.postValue("removePicture: $e")
            }
        }
    }
}