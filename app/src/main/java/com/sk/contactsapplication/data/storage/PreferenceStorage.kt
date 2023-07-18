package com.sk.contactsapplication.data.storage

interface PreferenceStorage {
    fun setString(key: String, value: String)
    fun getString(key: String): String?
    fun clear()
}