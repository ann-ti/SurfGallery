package com.annti.surfgallery.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.annti.surfgallery.R
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.databinding.FragmentSearchBinding
import com.annti.surfgallery.presentation.home.GalleryViewModel
import com.annti.surfgallery.presentation.home.adapter.GalleryAdapter
import com.annti.surfgallery.presentation.home.adapter.GalleryAdapterDelegate
import com.annti.surfgallery.utils.GridSpacingItemDecoration
import com.annti.surfgallery.utils.onQueryTextChange
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSearch : Fragment(R.layout.fragment_search), GalleryAdapterDelegate.ItemSelected {

    private val viewModel by viewModel<SearchViewModel>()
    private val viewModelGallery by viewModel<GalleryViewModel>()
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
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        setRecyclerViewListMovie()
        search()
    }

    private fun setRecyclerViewListMovie() {
        with(binding.listSearch) {
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

    private fun search() {
        val titlePicture = binding.searchView.onQueryTextChange()
        viewModel.searchPicture(titlePicture)

        lifecycleScope.launchWhenStarted {
            viewModel.pictureList.collect {
                adapterGallery.items = it
            }
        }
        viewModel.errorData.observe(viewLifecycleOwner){ errorText ->
            binding.frameAlarmError.setText(errorText)
        }
        viewModel.errorViewData.observe(viewLifecycleOwner){ error ->
            binding.frameAlarmError.setImage(R.drawable.ic_emotion_unhappy)
            showError(error)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            showProgress(it)
        }
        viewModel.mainView.observe(viewLifecycleOwner){ show ->
            binding.frameAlarmError.setImage(R.drawable.ic_search_eye_line)
            binding.frameAlarmError.setText("Введите ваш запрос")
            showViewSearch(show)
        }

    }

    private fun showViewSearch(show: Boolean){
        binding.listSearch.isVisible = !show
        binding.frameAlarmError.isVisible = show
    }

    private fun showError(show: Boolean) {
        binding.listSearch.isVisible = !show
        binding.frameAlarmError.isVisible = show
    }

    private fun showProgress(show: Boolean) {
        binding.progressBar.isVisible = show
        binding.listSearch.isVisible = !show
    }

    override fun onItemSelected(item: Picture) {
        val bundle = Bundle()
        bundle.putParcelable("item", item)
        val navController = Navigation.findNavController(
            requireActivity(),
            R.id.activityNavHost
        )
        navController.navigate(R.id.detailsFragment, bundle)
    }

    override fun addToFavorite(item: Picture) {
        viewModelGallery.saveOrRemovePicture(item)
    }
}