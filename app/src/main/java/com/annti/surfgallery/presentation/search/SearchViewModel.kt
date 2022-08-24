package com.annti.surfgallery.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.domain.GalleryUseCase
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.*

class SearchViewModel(
    private val galleryUseCase: GalleryUseCase
) : ViewModel() {

    private var pictureListStateFlow = MutableStateFlow<List<Picture>>(emptyList())
    val pictureList: StateFlow<List<Picture>> = pictureListStateFlow

    val errorData = LiveEvent<String>()
    val errorViewData = LiveEvent<Boolean>()
    var loadingLiveData = MutableLiveData<Boolean>()
    val mainView = MutableLiveData<Boolean>()

    fun searchPicture(query: Flow<String>) {
        mainView.postValue(true)
        query
            .debounce(200)
            .filter { query ->
                if (query.isEmpty()) {
                    emptyList<Picture>()
                    return@filter false
                } else return@filter true
            }
            .distinctUntilChanged()
            .onEach {
                mainView.postValue(false)
                loadingLiveData.postValue(true)
            }
            .mapLatest { query ->
                galleryUseCase.search(query)
            }
            .onEach {
                loadingLiveData.postValue(false)
                mainView.postValue(false)
                if (it.isNullOrEmpty()) {
                    errorData.postValue("По этому запросу нет результатов,\n" +
                            "попробуйте другой запрос")
                    errorViewData.postValue(true)
                } else {
                    errorViewData.postValue(false)
                }
                pictureListStateFlow.value = it
            }
            .launchIn(viewModelScope)
    }

}