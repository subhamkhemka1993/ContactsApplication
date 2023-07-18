package com.sk.contactsapplication.helpers

import java.util.regex.Pattern

object Constants {

    const val API = "api"
    const val BASE_URL = "https://reqres.in/$API/"

    /* SHARE_PREFERENCE_KEYS */
    const val AUTHORIZATION_TOKEN = "authorization_token"

    /* API ENDPOINTS */
    const val API_LOGIN = "login"
    const val API_REGISTER = "register"
    const val API_USERS = "users"


    const val APP_SHARED_PREFERENCE = "Contacts_Application"
    const val NETWORK_ERROR = "NetworkError"


    val EMAIL_REGEX_PATTERN: Pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

}