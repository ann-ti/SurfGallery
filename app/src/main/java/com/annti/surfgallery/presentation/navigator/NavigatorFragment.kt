package com.annti.surfgallery.presentation.navigator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.annti.surfgallery.R
import com.annti.surfgallery.databinding.FragmentNavigateBinding
import com.annti.surfgallery.utils.setupWithNavController

class NavigatorFragment : Fragment(R.layout.fragment_navigate) {

    private lateinit var binding: FragmentNavigateBinding

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNavigateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.home_nav_graph,
            R.navigation.favorite_nav_graph,
            R.navigation.account_nav_graph
        )

        binding.bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.container_navigate,
            intent = requireActivity().intent
        )
    }

}