package com.openingtablecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OpeningViewModelFactory(private val logPrefix: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpeningViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OpeningViewModel(logPrefix) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}