package com.xnfl16.elaundrie.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aemerse.slider.model.CarouselItem
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.utils.Constant.IMG1_URL
import com.xnfl16.elaundrie.utils.Constant.IMG2_URL
import com.xnfl16.elaundrie.utils.Constant.IMG3_URL

class MainViewModel: ViewModel() {

    private var _isConnected = MutableLiveData<Boolean>()
    val isConnected : LiveData<Boolean> get() = (_isConnected)

    fun connectionFailed(){
        _isConnected.value = false
    }
    fun connectionSuccess(){
        _isConnected.value = true
    }

    val imgUrl = mutableListOf<CarouselItem>()
    init{
        imgUrl.add(CarouselItem(IMG1_URL))
        imgUrl.add(CarouselItem(IMG2_URL))
        imgUrl.add(CarouselItem(IMG3_URL))
    }
}