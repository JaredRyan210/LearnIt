package com.hfad.finalproject_team_temp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {
    private val _welcomeMessage = MutableLiveData<String>()

    val welcomeMessage: LiveData<String> get() = _welcomeMessage

    fun setWelcomeMessage(username: String) {
        _welcomeMessage.value = "Welcome, $username!"
    }
}