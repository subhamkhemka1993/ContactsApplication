package com.sk.contactsapplication.presentation.login_register

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import com.sk.contactsapplication.R
import com.sk.contactsapplication.helpers.ToolbarTypes
import com.sk.contactsapplication.base.BaseFragment
import com.sk.contactsapplication.databinding.FragmentLoginRegisterBinding
import com.sk.contactsapplication.helpers.hideSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [LoginRegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class LoginRegisterFragment : BaseFragment() {

    private val loginRegisterViewModel: LoginRegisterViewModel by viewModels()
    private var binding: FragmentLoginRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar(ToolbarTypes.None)
        initObserver()
        binding?.txtSignUp?.apply {
            paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
            setOnClickListener {
                loginRegisterViewModel.changeUI()
            }
        }
        binding?.btnLogin?.setOnClickListener {
            loginRegisterViewModel.register(email = binding?.edtEmail?.text.toString(), password = binding?.edtPassword?.text.toString())
        }
    }

    override fun initObserver() {
        super.initObserver()
        loginRegisterViewModel.loginRegistrationUIState.observe(viewLifecycleOwner) { loginRegistrationUIState ->
            when (loginRegistrationUIState) {
                is LoginRegisterUIState.EmptyInputFields -> {
                    showToast(loginRegistrationUIState.error)
                }

                is LoginRegisterUIState.Error -> {
                    showToast(loginRegistrationUIState.error)
                }

                is LoginRegisterUIState.InvalidEmail -> {
                    showToast(loginRegistrationUIState.error)
                }

                LoginRegisterUIState.Login -> {
                    changeUI(isLogin = true)
                }

                LoginRegisterUIState.LoginSuccess -> {
                    navigateToHome()
                }

                LoginRegisterUIState.Register -> {
                    changeUI(isLogin = false)
                }

                is LoginRegisterUIState.RegistrationSuccess -> {
                    clearInputFields()
                    hideSoftKeyboard()
                    showToast(loginRegistrationUIState.message)
                }
            }
        }
    }

    private fun clearInputFields() {
        binding?.apply {
            edtEmail.setText("")
            edtPassword.setText("")
        }
    }

    private fun navigateToHome() {
        hideSoftKeyboard()
        val navBuilder = NavOptions.Builder()
        val navOptions = navBuilder.setPopUpTo(R.id.loginEmailFragment, true).build()
        navigateTo(R.id.action_to_home, navOptions = navOptions)
    }

    private fun changeUI(isLogin: Boolean) {
        binding?.apply {
            txtSignUp.text = getString(if (isLogin) {
                R.string.sign_up_label
            } else {
                R.string.sign_in_label
            })
            txtNewUserLabel.text = getString(if (isLogin) {
                R.string.new_user_label
            } else {
                R.string.already_registered_label
            })
            btnLogin.text = getString(if (isLogin) {
                R.string.label_login
            } else {
                R.string.label_register
            })
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = LoginRegisterFragment()
    }
}