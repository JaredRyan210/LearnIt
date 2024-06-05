package com.hfad.finalproject_team_temp

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "username"
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun login() {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }
    fun logout() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false)
        editor.apply()
    }
    fun loadUsername(): String? {
        return sharedPreferences.getString(KEY_USERNAME, null)
    }

    fun saveUsername(username: String) {
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }
}