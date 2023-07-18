package com.sk.contactsapplication.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.sk.contactsapplication.helpers.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) : PreferenceStorage {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.APP_SHARED_PREFERENCE, Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}