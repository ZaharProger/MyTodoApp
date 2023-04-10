package com.example.mytodoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContentViewModel : ViewModel() {
    private var _topAppBarHeader: MutableLiveData<String> = MutableLiveData("")
    val topAppBarHeader: LiveData<String>
        get() = _topAppBarHeader

    private var _isFabActive: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFabActive: LiveData<Boolean>
        get() = _isFabActive

    private var _isDialogOpen: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDialogOpen: LiveData<Boolean>
        get() = _isDialogOpen

    private var _isDeleteActive: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDeleteActive: LiveData<Boolean>
        get() = _isDeleteActive

    fun setTopAppBarHeader(headerText: String) {
        _topAppBarHeader.value = headerText
    }

    fun setFabState(fabState: Boolean) {
        _isFabActive.value = fabState
    }

    fun setDialogState(dialogState: Boolean) {
        _isDialogOpen.value = dialogState
    }

    fun setDeleteState(deleteState: Boolean) {
        _isDeleteActive.value = deleteState
    }
}