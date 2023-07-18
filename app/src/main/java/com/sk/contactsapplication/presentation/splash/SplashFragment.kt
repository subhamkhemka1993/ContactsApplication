package com.sk.contactsapplication.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import com.sk.contactsapplication.R
import com.sk.contactsapplication.databinding.FragmentSplashBinding
import com.sk.contactsapplication.helpers.ToolbarTypes
import com.sk.contactsapplication.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SplashFragment : BaseFragment() {


    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val fragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)
        return fragmentSplashBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar(ToolbarTypes.None)
        splashViewModel.checkUserValidity()
        splashViewModel.isValidUser.observe(viewLifecycleOwner) { isValid ->
            val navBuilder = NavOptions.Builder()
            val navOptions =
                navBuilder.setPopUpTo(R.id.splashFragment, true).build()
            if (isValid) {
                navigateTo(R.id.action_to_home, navOptions = navOptions)
            } else {
                navigateTo(R.id.action_to_login, navOptions = navOptions)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = SplashFragment()
    }
}