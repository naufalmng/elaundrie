package com.xnfl16.elaundrie.ui.data_pelanggan

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.xnfl16.elaundrie.core.data.source.model.Pelanggan
import com.xnfl16.elaundrie.core.data.source.network.ElaundrieWorker
import com.xnfl16.elaundrie.core.data.source.network.State
import com.xnfl16.elaundrie.utils.Constant
import com.xnfl16.elaundrie.utils.getCurrentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.util.*
import java.util.concurrent.TimeUnit


class DataPelangganViewModel : ViewModel() {
    companion object {
        const val TAG = "DataPelangganViewModel"
    }

    private val db = Firebase.firestore

    private var _status = MutableLiveData<State>()
    val status: LiveData<State> get() = (_status)

    private var _errorMsg = MutableLiveData<String?>()
    val errorMsg: LiveData<String?> get() = (_errorMsg)

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
        val total = hitungDiskon(jumlahKg, diskon,layanan)

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

        viewModelScope.launch(Dispatchers.IO)  {
            _errorMsg.postValue(null)
            _status.postValue(State.LOADING)
            db.collection("Pelanggan").document(id).set(data)
                .addOnSuccessListener { documentRef ->
                    _status.postValue(State.INSERT_SUCCESS)
                }

                .addOnFailureListener { e ->
                    Log.d("dbsitory: ", "Error adding document", e)
                    _status.postValue(State.INSERT_FAILED)
                    _errorMsg.postValue(e.toString())
                }
        }
    }

    fun deleteData(docId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _errorMsg.postValue(null)
            _status.postValue(State.LOADING)
            db.collection("Pelanggan").document(docId).delete()
                .addOnSuccessListener {
                    _status.postValue(State.DELETE_SUCCESS)
                }
                .addOnFailureListener { e ->
                    Log.d("dbsitory: ", "Error adding document", e)
                    _status.postValue(State.DELETE_FAILED)
                    _errorMsg.postValue(e.toString())
                }
        }
    }

    fun updateData(
        data: Pelanggan
    ) {
        val jumlah: Double = data.jumlah!!.toDouble()
        val diskon: Double = data.diskon!!.toDouble()

        val total = hitungDiskon(jumlah, diskon,data.layanan.toString())

        Log.d(TAG, data.id.toString())
        Log.d(TAG, data.nama.toString())

        viewModelScope.launch(Dispatchers.IO) {
            _errorMsg.postValue(null)
            _status.postValue(State.LOADING)
            db.collection("Pelanggan").document(data.id.toString()).update(
                "tanggal", data.tanggalDanWaktu,
                "nama", data.nama,
                "alamat", data.alamat,
                "jumlah", jumlah.toInt().toString(),
                "status", data.status,
                "diskon", diskon.toInt().toString(),
                "layanan", data.layanan,
                "total", total
            )
                .addOnSuccessListener { documentRef ->
                    _status.postValue(State.UPDATE_SUCCESS)
                }

                .addOnFailureListener { e ->
                    Log.d("dbsitory: ", "Error adding document", e)
                    _status.postValue(State.UPDATE_FAILED)
                    _errorMsg.postValue(e.toString())
                }
        }

    }

    fun initDataPelanggan() {
        val dataList: ArrayList<Pelanggan> = arrayListOf()
        _dataPelanggan.value?.clear()
        _status.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            _errorMsg.postValue(null)
            db.collection("Pelanggan").addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen Failed", e)
                    _errorMsg.postValue(e.toString())
                    _status.postValue(State.FAILED)
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
                    _status.postValue(State.SUCCESS)
                }
            }
        }
    }

    private fun generateUniqueId(): String {
        val uuid =
            String.format("%040d", BigInteger(UUID.randomUUID().toString().replace("-", ""), 16))
        return uuid.substring(uuid.length - 10)
    }


    private fun hitungDiskon(jumlah: Double, diskon: Double,layanan: String): String {
        val hargaPerKg = when(layanan){
            "Flash 1 Hour" -> 8000
            "Express 1 Day" -> 7000
            else -> 6000
        }
        val hargaAsli = hargaPerKg * jumlah
        val jumlahDiskon = (diskon / 100) * hargaAsli
        return if (jumlah != 0.0) {
            (hargaAsli - jumlahDiskon).toInt().toString()
        } else hargaAsli.toInt().toString()
    }

    fun getCurrentData(s: String): ArrayList<Pelanggan>{
        val dataList: ArrayList<Pelanggan> = arrayListOf()
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("Pelanggan").whereNotEqualTo("nama",s)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen Failed", e)
                        _errorMsg.postValue(e.toString())
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
        return dataList
    }

    fun searchData(ctx: Context, s: String) {
        val dataList: ArrayList<Pelanggan> = arrayListOf()

        viewModelScope.launch(Dispatchers.IO) {
            db.collection("Pelanggan").whereEqualTo("nama", s)
                .get()
                .addOnCompleteListener {
                    _dataPelanggan.value?.clear()
                    for (doc in it.result) {
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
                .addOnFailureListener {
                    Toast.makeText(ctx, "Searching failed..", Toast.LENGTH_SHORT).show()
                }
        }

    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            _errorMsg.postValue(null)
            _status.postValue(State.LOADING)
            db.collection("Pelanggan").get()
                .addOnSuccessListener {
                    for (doc in it) {
                        doc.reference.delete()
                        _status.postValue(State.DELETE_ALL_SUCCESS)
                    }
                }
                .addOnFailureListener {
                    _status.postValue(State.DELETE_ALL_FAILED)
                    _errorMsg.postValue(it.toString())
                }
        }
    }

}