package com.annti.surfgallery.presentation.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annti.surfgallery.R
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.databinding.ItemGalleryBinding
import com.annti.surfgallery.utils.inflate
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class GalleryAdapterDelegate(private val itemSelected: ItemSelected) :
    AbsListItemAdapterDelegate<Picture, Picture, GalleryAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Picture,
        items: MutableList<Picture>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_gallery), itemSelected)
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
        private val binding = ItemGalleryBinding.bind(view)

        fun bind(picture: Picture) {
            binding.buttonFavorite.setOnClickListener {
                itemSelected.addToFavorite(picture)
            }
            if (picture.isFavorite){
                binding.buttonFavorite.setImageResource(R.drawable.ic_heart_fill)
            }else{
                binding.buttonFavorite.setImageResource(R.drawable.ic_heart_line)
            }
            binding.txtTitleGallery.text = picture.title
            Glide.with(itemView)
                .load(picture.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imageGallery)
        }

    }

    interface ItemSelected {
        fun onItemSelected(item: Picture)
        fun addToFavorite(item: Picture)
    }
}