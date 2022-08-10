package com.annti.surfgallery.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.annti.surfgallery.R
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.databinding.FragmentHomeBinding
import com.annti.surfgallery.presentation.home.adapter.GalleryAdapter
import com.annti.surfgallery.presentation.home.adapter.GalleryAdapterDelegate
import com.annti.surfgallery.utils.GridSpacingItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home), GalleryAdapterDelegate.ItemSelected {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModel<GalleryViewModel>()
    private val adapterGallery: GalleryAdapter by lazy { GalleryAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewGallery()
        getGalleryList()
    }

    private fun setRecyclerViewGallery() {
        with(binding.listGallery) {
            adapter = adapterGallery
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(false)
            val spanCount = 2 // 2 columns
            val spacing = 20 // 20px
            val includeEdge = true
            this.addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount, spacing, includeEdge
                )
            )
        }
    }

    private fun getGalleryList() {
        viewModel.getGalleryList()
        viewModel.galleryList.observe(viewLifecycleOwner) {
            adapterGallery?.items = it
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemSelected(item: Picture) {
        val titleHome = "Галерея"
        val bundle = Bundle()
        bundle.putParcelable("item", item)
        bundle.putString("title", titleHome)
        val navController = Navigation.findNavController(
            requireActivity(),
            R.id.activityNavHost
        )
        navController.navigate(R.id.detailsFragment, bundle)
    }

    override fun addToFavorite(item: Picture) {
        viewModel.saveOrRemovePicture(item)
    }
}