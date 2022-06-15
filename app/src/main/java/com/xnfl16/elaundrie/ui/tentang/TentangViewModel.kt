package com.xnfl16.elaundrie.ui.tentang

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xnfl16.elaundrie.core.data.repository.AppRepository
import kotlinx.coroutines.launch

class TentangViewModel(private val repo: AppRepository) : ViewModel() {
    private var _copyrightString =  MutableLiveData<String>()
    val copyrightString: LiveData<String> get() = (_copyrightString)

    fun initCopyrightString(){
        viewModelScope.launch {
            val response = repo.getCopyright()
            if(response.isSuccessful){
                let {
                    _copyrightString.postValue(response.body()?.copyright!!)
                }
            }
        }
    }
}