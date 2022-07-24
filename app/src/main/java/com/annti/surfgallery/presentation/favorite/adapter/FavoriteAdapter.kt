package com.annti.surfgallery.presentation.favorite.adapter

import androidx.recyclerview.widget.DiffUtil
import com.annti.surfgallery.data.model.Picture
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class FavoriteAdapter(itemSelected: FavoriteAdapterDelegate.ItemSelected)  :
    AsyncListDifferDelegationAdapter<Picture>(ApplicationDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(FavoriteAdapterDelegate(itemSelected))
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