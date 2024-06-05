package com.hfad.finalproject_team_temp.ui.quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizzesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is quizzes Fragment"
    }
    val text: LiveData<String> = _text
}