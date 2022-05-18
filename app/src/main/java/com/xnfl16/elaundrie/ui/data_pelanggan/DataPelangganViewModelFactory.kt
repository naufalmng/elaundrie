@file:Suppress("UNCHECKED_CAST")

package com.xnfl16.elaundrie.ui.data_pelanggan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.IllegalArgumentException

class DataPelangganViewModelFactory(private val db: FirebaseFirestore): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DataPelangganViewModel::class.java)){
            return DataPelangganViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}