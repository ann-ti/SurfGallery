package com.annti.surfgallery.presentation.details

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.annti.surfgallery.R
import com.annti.surfgallery.data.model.Picture
import com.annti.surfgallery.databinding.FragmentDetailsBinding
import com.annti.surfgallery.utils.dateFormat
import com.bumptech.glide.Glide

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = requireArguments().getString("title")
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        bind()
    }

    private fun bind() {
        val pictureData = requireArguments().getParcelable<Picture>("item")
        Glide.with(requireContext())
            .load(pictureData?.photoUrl)
            .into(binding.imgDetails)
        binding.txtTitleDetails.text = pictureData?.title
        binding.txtDateDetails.text = pictureData?.let { dateFormat(it.publicationDate) }
        binding.txtDescriptionDetails.text = pictureData?.content
    }
}