package com.xnfl16.elaundrie.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.DataStorePreferences
import com.xnfl16.elaundrie.core.data.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

//    private var fragmentPref: String? = null
//    private lateinit var dataStorePref: DataStorePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupDataStorePref()
        navigateToMainFragment()
    }

//    private fun setupDataStorePref() {
//        dataStorePref = DataStorePreferences(requireContext(),requireContext().dataStore)
//        dataStorePref.preferenceFlow.asLiveData()
//            .observe(viewLifecycleOwner){
//
//            }
//    }

    private fun navigateToMainFragment(){
        lifecycleScope.launch {
            delay(1000)
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToMainFragment())
        }
    }

//    private fun setupFragmentPref(fragmentName: String) {
//        when(fragmentName){
//            "mainFragment" -> findNavController().navigate(R.id.go_to_mainFragment)
//            "tentangFragment" -> findNavController().navigate(R.id.go_to_tentangFragment)
//            "dataPelangganFragment" -> findNavController().navigate(R.id.go_to_dataPelangganFragment)
//            else -> navigateToMainFragment()
//        }
//    }
}