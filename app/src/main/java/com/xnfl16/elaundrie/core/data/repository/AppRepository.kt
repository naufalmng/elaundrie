package com.xnfl16.elaundrie.core.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppRepository(private val db: Firebase) {
    fun insertData(collectionName: String,pelanggan: Any) = db.firestore.collection(collectionName).add(pelanggan)
}