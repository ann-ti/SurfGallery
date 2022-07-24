package com.annti.surfgallery.presentation.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.annti.surfgallery.R
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.databinding.FragmentFavoriteBinding
import com.annti.surfgallery.presentation.favorite.adapter.FavoriteAdapter
import com.annti.surfgallery.presentation.favorite.adapter.FavoriteAdapterDelegate
import com.annti.surfgallery.presentation.home.GalleryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite),
    FavoriteAdapterDelegate.ItemSelected {

    private lateinit var binding: FragmentFavoriteBinding
    private val adapterFav: FavoriteAdapter by lazy { FavoriteAdapter(this) }
    private val viewModel: FavoriteViewModel by viewModel()
    private val galleryViewModel: GalleryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewFavorite()
        getFavPicture()
    }

    private fun setRecyclerViewFavorite() {
        with(binding.listFavorite) {
            adapter = adapterFav
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
        }
    }

    private fun getFavPicture() {
        viewModel.getFavorites()
        lifecycleScope.launchWhenCreated {
            viewModel.picture.collect {
                adapterFav.items = it
            }
        }
    }

    override fun onItemSelected(item: Picture) {
        TODO("Not yet implemented")
    }

    override fun removeFavorite(item: Picture) {
        AlertDialog.Builder(context)
            .setMessage("Вы точно хотите удалить из избранного?")
            .setPositiveButton("да, точно") { dialog, which ->
                galleryViewModel.removePicture(item)
            }
            .setNegativeButton("нет", null)
            .create()
            .show()
    }
}