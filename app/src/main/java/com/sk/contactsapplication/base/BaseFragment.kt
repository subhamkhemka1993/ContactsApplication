package com.sk.contactsapplication.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.sk.contactsapplication.helpers.ToolbarTypes
import com.sk.contactsapplication.helpers.UIText
import com.sk.contactsapplication.presentation.MainActivity

abstract class BaseFragment : Fragment() {

    open fun setUpToolbar(type: ToolbarTypes) {
        (activity as? MainActivity)?.setUpToolbar(type)
    }

    fun navigateTo(actionId: Int, args: Bundle? = null, navOptions: NavOptions? = null) {
        findNavController().navigate(
            actionId,
            args,
            navOptions
        )
    }

    open fun initObserver() {}

    open fun showToast(messageText: UIText) {
        val message = when (messageText) {
            is UIText.DynamicString -> {
                messageText.value
            }

            is UIText.StringResource -> {
                getString(messageText.id)
            }
        }
        (activity as? MainActivity)?.showToast(message)
    }
}