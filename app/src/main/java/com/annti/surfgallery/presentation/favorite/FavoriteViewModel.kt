package com.annti.surfgallery.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.domain.GalleryUseCase
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
        galleryUseCase.getFavorites()
            .onEach {
                pictureMutableState.value = it
            }
            .launchIn(viewModelScope)
    }

}