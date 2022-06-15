package com.xnfl16.elaundrie.ui.main


import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.aemerse.slider.model.CarouselItem
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.source.network.ElaundrieWorker
import com.xnfl16.elaundrie.ui.data_pelanggan.DataPelangganViewModel
import com.xnfl16.elaundrie.utils.Constant
import com.xnfl16.elaundrie.utils.Constant.CHANNEL_ID
import com.xnfl16.elaundrie.utils.Constant.IMG1_URL
import com.xnfl16.elaundrie.utils.Constant.IMG2_URL
import com.xnfl16.elaundrie.utils.Constant.IMG3_URL
import java.util.concurrent.TimeUnit

class MainViewModel: ViewModel() {

    private var _isConnected = MutableLiveData<Boolean>()
    val isConnected : LiveData<Boolean> get() = (_isConnected)

    fun connectionFailed(){
        _isConnected.value = false
    }
    fun connectionSuccess(){
        _isConnected.value = true
    }
    fun setupWorkerSchedule(app: Application){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

        val request = OneTimeWorkRequestBuilder<ElaundrieWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(app).enqueueUniqueWork(
            CHANNEL_ID,
            ExistingWorkPolicy.APPEND_OR_REPLACE,request)
    }

    val imgUrl = mutableListOf<CarouselItem>()
    init{
        imgUrl.add(CarouselItem(IMG1_URL))
        imgUrl.add(CarouselItem(IMG2_URL))
        imgUrl.add(CarouselItem(IMG3_URL))
    }
}