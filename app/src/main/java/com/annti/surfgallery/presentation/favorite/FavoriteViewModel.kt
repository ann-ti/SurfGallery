package com.annti.surfgallery.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.domain.GalleryUseCase
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.*

class FavoriteViewModel(
    private val galleryUseCase: GalleryUseCase
) : ViewModel() {

    private val pictureMutableState = MutableStateFlow<List<Picture>>(emptyList())
    val picture: StateFlow<List<Picture>> = pictureMutableState

    private var errorData = LiveEvent<String>()
    private var errorViewData = LiveEvent<Boolean>()

    val error: LiveEvent<String>
        get() = errorData
    val errorView: LiveEvent<Boolean>
        get() = errorViewData

    fun getFavorites() {
        galleryUseCase.getPictureDb()
            .onEach {
                val vfbg = it.filter {
                    it.isFavorite
                }
                pictureMutableState.value = vfbg
            }
            .launchIn(viewModelScope)
    }

}