package com.annti.surfgallery.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.annti.surfgallery.R
import com.annti.surfgallery.databinding.FragmentLoginBinding
import com.annti.surfgallery.utils.LoadState
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtEditPassword.doOnTextChanged { password, _, _, _ ->
            viewModel.setPassword(password.toString())
        }
        observeLoginError()
        observePasswordError()
        observeLoadState()
        initLoginMask()

        binding.buttonLogin.setOnClickListener {
            viewModel.loginAuth()
        }
    }

    private fun observeLoadState() {
        viewModel.loadState.observe(viewLifecycleOwner) { loadState ->
            when (loadState) {
                LoadState.LOADING -> {
                    binding.buttonLogin.isLoading = true
                    binding.blockContentContainer.isVisible = true
                }
                LoadState.ERROR -> {
                    binding.buttonLogin.isLoading = false
                    binding.blockContentContainer.isVisible = false
                    Snackbar.make(
                        binding.root,
                        "Логин или пароль введен неправильно",
                        Snackbar.LENGTH_LONG
                    ).setAnchorView(binding.buttonLogin).show()
                }
                LoadState.SUCCESS -> {
                    binding.buttonLogin.isLoading = false
                    binding.blockContentContainer.isVisible = false
                    findNavController().navigate(R.id.action_loginFragment_to_navigatorFragment)
                }
            }
        }
    }

    private fun initLoginMask() {
        installOn(
            binding.txtEditEmail,
            PHONE_MASK,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    viewModel.setLogin(extractedValue)
                }
            }
        )
    }

    private fun observeLoginError() {
        viewModel.loginErrorLiveData.observe(viewLifecycleOwner) { loginError ->
            when (loginError) {
                LoginError.EMPTY -> {
                    binding.txtInputEmail.error = getString(R.string.login_empty_error)
                }
                LoginError.NOT_VALID -> {
                    binding.txtInputEmail.error = getString(R.string.login_not_valid_error)
                }
                LoginError.VALID -> {
                    binding.txtInputEmail.error = null
                }
                else -> {
                    //do nothing
                }
            }
        }
    }

    private fun observePasswordError() {
        viewModel.passwordErrorLiveData.observe(viewLifecycleOwner) { passwordError ->
            when (passwordError) {
                PasswordError.EMPTY -> {
                    binding.txtInputPassword.error = getString(R.string.password_empty_error)
                }
                PasswordError.NOT_VALID -> {
                    binding.txtInputPassword.error = getString(R.string.password_not_valid_error)
                }
                PasswordError.VALID -> {
                    binding.txtInputPassword.error = null
                }
                else -> {
                    //do nothing
                }
            }
        }
    }

    companion object {
        const val PHONE_MASK = "+ 7 ([000]) [000] [00] [00]"
    }

}