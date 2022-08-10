package com.annti.surfgallery.presentation.favorite.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annti.surfgallery.R
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.databinding.ItemFavoriteBinding
import com.annti.surfgallery.utils.dateFormat
import com.annti.surfgallery.utils.inflate
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class FavoriteAdapterDelegate(private val itemSelected: ItemSelected) :
    AbsListItemAdapterDelegate<Picture, Picture, FavoriteAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Picture,
        items: MutableList<Picture>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_favorite), itemSelected)
    }

    override fun onBindViewHolder(
        item: Picture,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        private val view: View,
        private val itemSelected: ItemSelected
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemFavoriteBinding.bind(view)

        fun bind(picture: Picture) {
            binding.buttonFavorite.setOnClickListener {
                itemSelected.removeFavorite(picture)
            }
            view.setOnClickListener {
                itemSelected.onItemSelected(picture)
            }
            view.setOnClickListener { itemSelected.onItemSelected(picture) }
            binding.txtDescriptionFavorite.text = picture.content
            binding.txtDateFavorite.text = dateFormat(picture.publicationDate)
            binding.txtTitleFavorite.text = picture.title
            Glide.with(itemView)
                .load(picture.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageFavorite)
        }
    }

    interface ItemSelected {
        fun onItemSelected(item: Picture)
        fun removeFavorite(item: Picture)
    }
}