package com.annti.surfgallery.presentation.account

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.annti.surfgallery.R
import com.annti.surfgallery.databinding.FragmentAccountBinding
import com.annti.surfgallery.presentation.auth.LoginViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment(R.layout.fragment_account) {

    private lateinit var binding: FragmentAccountBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()
        logout()

        viewModel.error.observe(viewLifecycleOwner){
            Snackbar.make(
                view,
                "Отсутствует интернет-соединение. Попробуйте позже",
                Snackbar.LENGTH_LONG
            )
                .setBackgroundTint(resources.getColor(R.color.error_color))
                .show()
            true
        }
    }

    private fun logout() {
        binding.buttonLogout.setOnClickListener {
            AlertDialog.Builder(context)
                .setMessage("Вы точно хотите выйти из приложения?")
                .setPositiveButton("да, точно") { dialog, which ->
                    viewModel.logout()
                    navigate()
                }
                .setNegativeButton("нет", null)
                .create()
                .show()
        }
    }

    private fun navigate() {
        val navController = Navigation.findNavController(
            requireActivity(),
            R.id.activityNavHost
        )
        val mainGraph = navController.navInflater.inflate(R.navigation.app_nav_graph)

        mainGraph.setStartDestination(R.id.loginFragment)

        navController.graph = mainGraph
    }

    private fun bind() {
        viewModel.getUserInfo()
        viewModel.userInfo.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it?.avatar)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imgAccount)
            binding.txtEditCity.text = it?.city
            binding.txtEditEmail.text = it?.email
            binding.txtAccountName.text = "${it?.firstName} ${it?.lastName}"
            binding.txtAbout.text = "\"" + it?.about + "\""
            binding.txtEditNumberTelephone.text = it?.phone
        }
    }
}