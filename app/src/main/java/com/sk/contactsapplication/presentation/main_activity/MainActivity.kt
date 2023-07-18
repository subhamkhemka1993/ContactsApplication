package com.sk.contactsapplication.presentation.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.sk.contactsapplication.R
import com.sk.contactsapplication.databinding.ActivityMainBinding
import com.sk.contactsapplication.helpers.ToolbarTypes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null

    private val mainActivityViewModel: MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        initToolBar()
    }

    private fun initToolBar() {
        binding?.layoutToolbar?.apply {
            tvLogout.setOnClickListener {
                onLogoutClicked()
            }
        }
    }

    private fun onLogoutClicked() {
        mainActivityViewModel.onLogout()
        val navBuilder = NavOptions.Builder()
        val navOptions = navBuilder.setPopUpTo(R.id.nav_graph, true).build()
        navController?.navigate(R.id.loginEmailFragment, args = null, navOptions = navOptions)
    }


    fun setUpToolbar(type: ToolbarTypes) {
        when (type) {
            ToolbarTypes.Home -> {
                binding?.layoutToolbar?.toolbar?.isVisible = true
            }

            ToolbarTypes.None -> {
                binding?.layoutToolbar?.toolbar?.isVisible = false
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}