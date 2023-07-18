package com.sk.contactsapplication.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.contactsapplication.helpers.ToolbarTypes
import com.sk.contactsapplication.base.BaseFragment
import com.sk.contactsapplication.databinding.FragmentHomeBinding
import com.sk.contactsapplication.helpers.PaginationListener
import com.sk.contactsapplication.presentation.add_user.AddUserDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null
    private var userAdapter: UserAdapter? = null

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar(ToolbarTypes.Home)
        initObserver()
        userAdapter = UserAdapter { itemPosition ->
            homeViewModel.onItemTapped(itemPosition)
        }
        binding?.apply {
            recycler.apply {
                itemAnimator = null
                val linearLayoutManager = LinearLayoutManager(context)
                layoutManager = linearLayoutManager
                adapter = userAdapter
                addOnScrollListener(object : PaginationListener(layoutManager = linearLayoutManager, pageSize = homeViewModel.pageSize) {
                    override fun loadMoreItems() {
                        homeViewModel.fetchUsers()
                    }

                    override fun isLastPage() = homeViewModel.isLastPage

                    override fun isLoading() = homeViewModel.isLoading
                })
            }
            flAddUser.setOnClickListener {
                showAddUserDialog()
            }
        }
        homeViewModel.fetchUsers()
    }

    private fun showAddUserDialog() {
        val addUserDialogFragment = AddUserDialogFragment.newInstance()
        addUserDialogFragment.showNow(childFragmentManager, AddUserDialogFragment.TAG)
    }

    override fun initObserver() {
        super.initObserver()
        homeViewModel.homeUIState.observe(viewLifecycleOwner) { homeUIState ->
            when (homeUIState) {
                is HomeUIState.Users -> {
                    userAdapter?.setData(homeUIState.listOfUsers)
                }

                is HomeUIState.Error -> {
                    showToast(homeUIState.error)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}