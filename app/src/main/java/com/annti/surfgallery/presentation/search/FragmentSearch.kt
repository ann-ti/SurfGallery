package com.annti.surfgallery.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.annti.surfgallery.R
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.databinding.FragmentSearchBinding
import com.annti.surfgallery.presentation.home.adapter.GalleryAdapter
import com.annti.surfgallery.presentation.home.adapter.GalleryAdapterDelegate
import com.annti.surfgallery.utils.GridSpacingItemDecoration

class FragmentSearch: Fragment(R.layout.fragment_search), GalleryAdapterDelegate.ItemSelected {

    private lateinit var binding: FragmentSearchBinding
    private val adapterGallery: GalleryAdapter by lazy { GalleryAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewListMovie()
    }

    private fun setRecyclerViewListMovie(){
        with(binding.listGallery) {
            adapter = adapterGallery
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(false)
            val spanCount = 2
            val spacing = 20
            val includeEdge = true
            this.addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount, spacing, includeEdge
                )
            )
        }
    }

    override fun onItemSelected(item: Picture) {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(item: Picture) {
        TODO("Not yet implemented")
    }
}