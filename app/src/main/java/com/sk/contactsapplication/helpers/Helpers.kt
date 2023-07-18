package com.sk.contactsapplication.helpers

import android.app.Activity

import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment


sealed class UIText {
    data class DynamicString(val value: String) : UIText()
    data class StringResource(val id: Int) : UIText()
}

fun Int?.orZero(): Int = this ?: 0

fun Fragment.hideSoftKeyboard() {
    val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
}