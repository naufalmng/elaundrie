@file:Suppress("UNCHECKED_CAST")

package com.xnfl16.elaundrie.ui.tentang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xnfl16.elaundrie.core.data.repository.AppRepository
import java.lang.IllegalArgumentException

class TentangVIewModelFactory(private val repo: AppRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TentangViewModel::class.java)) {
            return TentangViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class")
    }
}