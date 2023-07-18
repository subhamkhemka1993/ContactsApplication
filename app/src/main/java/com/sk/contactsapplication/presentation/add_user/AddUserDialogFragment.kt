package com.sk.contactsapplication.presentation.add_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.sk.contactsapplication.databinding.DialogAddUserBinding
import com.sk.contactsapplication.helpers.UIText
import com.sk.contactsapplication.helpers.hideSoftKeyboard
import com.sk.contactsapplication.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserDialogFragment : DialogFragment() {

    private var binding: DialogAddUserBinding? = null

    private val addUserViewModel: AddUserViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAddUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        initObserver()
        binding?.btnAddUser?.setOnClickListener {
            addUserViewModel.addUser(name = binding?.edtName?.text.toString(), job = binding?.edtJob?.text.toString())
        }
    }

    private fun initObserver() {
        addUserViewModel.addUserUIState.observe(viewLifecycleOwner) { addUserUIState ->
            when (addUserUIState) {
                is AddUserUIState.EmptyInputFields -> {
                    showToast(addUserUIState.error)
                }

                is AddUserUIState.Error -> {
                    showToast(addUserUIState.error)
                }

                is AddUserUIState.UserAddedSuccessfully -> {
                    hideSoftKeyboard()
                    showToast(addUserUIState.message)
                    dismiss()
                }
            }
        }
    }

    private fun showToast(messageText: UIText) {
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

    companion object {
        const val TAG = "AddUserDialogFragment"
        fun newInstance() = AddUserDialogFragment()
    }
}