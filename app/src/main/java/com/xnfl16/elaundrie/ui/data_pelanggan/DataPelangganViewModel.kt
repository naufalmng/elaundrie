package com.xnfl16.elaundrie.ui.data_pelanggan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xnfl16.elaundrie.core.data.model.Pelanggan
import com.xnfl16.elaundrie.utils.getCurrentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.util.*


class DataPelangganViewModel() : ViewModel() {
    companion object {
        const val TAG = "DataPelangganViewModel"
    }

    private val db = Firebase.firestore

    private var _isInsertSuccess = MutableLiveData<Boolean>()
    val isInsertSuccess: LiveData<Boolean> get() = (_isInsertSuccess)

    private var _isUpdateSuccess = MutableLiveData<Boolean>()
    val isUpdateSuccess: LiveData<Boolean> get() = (_isUpdateSuccess)

    private var _dataPelanggan = MutableLiveData<ArrayList<Pelanggan>>()
    val dataPelanggan: LiveData<ArrayList<Pelanggan>> get() = (_dataPelanggan)


    init {
        initDataPelanggan()
    }


    fun insertData(
        nama: String,
        alamat: String,
        jumlahKg: Double,
        status: String,
        diskon: Double,
        layanan: String
    ) {
        val id = generateUniqueId()
        val total = hitungDiskon(jumlahKg, diskon)

        val data = hashMapOf(
            "id" to id,
            "tanggal" to getCurrentTime(),
            "nama" to nama,
            "alamat" to alamat,
            "jumlah" to jumlahKg.toInt().toString(),
            "status" to status,
            "diskon" to diskon.toInt().toString(),
            "layanan" to layanan,
            "total" to total
        )
        viewModelScope.launch(Dispatchers.IO) {
            _isInsertSuccess.postValue(false)
            db.collection("Pelanggan").add(data)
                .addOnSuccessListener { documentRef ->
                    Log.d("dbsitory: ", "DocumentSnapshot added with ID: ${documentRef.id}")
                    _isInsertSuccess.postValue(true)
                }

                .addOnFailureListener { e ->
                    Log.d("dbsitory: ", "Error adding document", e)
                    _isInsertSuccess.postValue(false)

                }
        }
    }

    fun updateData(data: Pelanggan
    ){
        val jumlah: Double = data.jumlah!!.toDouble()
        val diskon: Double = data.diskon!!.toDouble()

        val total = hitungDiskon(jumlah, diskon)

        viewModelScope.launch(Dispatchers.IO) {
            _isUpdateSuccess.postValue(false)
            db.collection("Pelanggan").document(data.id.toString()).update(
                "tanggal" , data.tanggalDanWaktu,
                "nama" , data.nama,
                "alamat" , data.alamat,
                "jumlah" , jumlah.toInt().toString(),
                "status" , data.status,
                "diskon" , diskon.toInt().toString(),
                "layanan", data.layanan,
                "total" , total
            )
                .addOnSuccessListener { documentRef ->
                    _isUpdateSuccess.postValue(true)
                }

                .addOnFailureListener { e ->
                    Log.d("dbsitory: ", "Error adding document", e)
                    _isUpdateSuccess.postValue(true)

                }
        }

    }

    private fun initDataPelanggan() {
        val dataList: ArrayList<Pelanggan> = arrayListOf()

        viewModelScope.launch(Dispatchers.IO) {
            db.collection("Pelanggan").addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen Failed", e)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    dataList.clear()
                    for (doc in snapshot.documents) {
                        val pelanggan = Pelanggan(
                            doc.getString("id"),
                            doc.getString("nama"),
                            doc.getString("alamat"),
                            doc.getString("tanggal"),
                            doc.getString("jumlah"),
                            doc.getString("diskon"),
                            doc.getString("total"),
                            doc.getString("status"),
                            doc.getString("layanan")
                        )
                        dataList.add(pelanggan)
                    }
                    _dataPelanggan.postValue(dataList)
                }
            }
        }
    }

    private fun generateUniqueId(): String {
        val uuid =
            String.format("%040d", BigInteger(UUID.randomUUID().toString().replace("-", ""), 16))
        return uuid.substring(uuid.length - 10)
    }


    private fun hitungDiskon(jumlah: Double, diskon: Double): String {

        val hargaPerKg = 6000
        val hargaAsli = hargaPerKg * jumlah
        val jumlahDiskon = (diskon / 100) * hargaAsli
        return if (jumlah != 0.0) {
            (hargaAsli - jumlahDiskon).toInt().toString()
        } else hargaAsli.toInt().toString()
    }

}