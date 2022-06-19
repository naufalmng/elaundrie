package com.xnfl16.elaundrie.ui.tentang

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xnfl16.elaundrie.core.data.repository.AppRepository
import com.xnfl16.elaundrie.core.data.source.network.State
import kotlinx.coroutines.launch

class TentangViewModel(private val repo: AppRepository) : ViewModel() {
    private var _copyrightString =  MutableLiveData<String>()
    val copyrightString: LiveData<String> get() = (_copyrightString)

    private var _status = MutableLiveData<State>()
    val status: LiveData<State> get() = (_status)

    fun initCopyrightString(){
        viewModelScope.launch {
            _status.postValue(State.LOADING)
            val response = repo.getCopyright()
            if(response.isSuccessful){
                let {
                    _copyrightString.postValue(response.body()?.copyright!!)
                    _status.postValue(State.SUCCESS)
                }
            }else{
                Log.e("TentangViewModel",response.message().toString())
                _status.postValue(State.FAILED)
            }
        }
    }
}