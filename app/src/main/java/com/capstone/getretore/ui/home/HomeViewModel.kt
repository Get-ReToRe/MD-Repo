package com.capstone.getretore.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}