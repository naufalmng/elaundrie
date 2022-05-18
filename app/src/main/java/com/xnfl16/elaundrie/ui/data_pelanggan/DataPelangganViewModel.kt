package com.xnfl16.elaundrie.ui.data_pelanggan

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.xnfl16.elaundrie.core.data.repository.AppRepository

class DataPelangganViewModel(db: Firebase) {
    private val appRepo: AppRepository = AppRepository(db)

    fun insertData(id: Int,)
}