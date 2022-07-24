package com.annti.surfgallery.presentation.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.annti.surfgallery.data.model.Picture
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class GalleryAdapter(itemSelected: GalleryAdapterDelegate.ItemSelected) :
    AsyncListDifferDelegationAdapter<Picture>(ApplicationDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(GalleryAdapterDelegate(itemSelected))
    }

    class ApplicationDiffUtilCallback : DiffUtil.ItemCallback<Picture>() {
        override fun areItemsTheSame(
            oldItem: Picture,
            newItem: Picture
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Picture,
            newItem: Picture
        ): Boolean {
            return oldItem == newItem
        }
    }
}