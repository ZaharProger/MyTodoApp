package com.example.mytodoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContentViewModel : ViewModel() {
    private var _topAppBarHeader: MutableLiveData<String> = MutableLiveData("")
    val topAppBarHeader: LiveData<String>
        get() = _topAppBarHeader

    fun setTopAppBarHeader(headerText: String) {
        _topAppBarHeader.value = headerText
    }
}