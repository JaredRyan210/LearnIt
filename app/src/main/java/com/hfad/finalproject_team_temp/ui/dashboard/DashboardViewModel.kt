package com.hfad.finalproject_team_temp.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import com.hfad.finalproject_team_temp.SessionManager

class DashboardViewModel : ViewModel() {

    val welcomeMessage = MutableLiveData<String>().apply { value = "" }
    fun setWelcomeMessage(username: String, context: Context) {
        val message = "Welcome, $username!"
        welcomeMessage.value = message
        saveUsername(username, context)
    }

    fun clearData() {
        welcomeMessage.value = ""
    }

    fun loadUsername(context: Context): String? {
        return SessionManager(context).loadUsername()
    }

    private fun saveUsername(username: String, context: Context) {
        SessionManager(context).saveUsername(username)
    }
}