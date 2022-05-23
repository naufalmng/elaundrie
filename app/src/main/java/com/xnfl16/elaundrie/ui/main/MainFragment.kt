package com.xnfl16.elaundrie.ui.main

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.marginStart
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.DataStorePreferences
import com.xnfl16.elaundrie.core.data.dataStore
import com.xnfl16.elaundrie.databinding.FragmentMainBinding
import com.xnfl16.elaundrie.ui.tentang.TentangFragment
import com.xnfl16.elaundrie.utils.enableOnClickAnimation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@DelicateCoroutinesApi
class MainFragment : Fragment() {
   companion object{
        const val TAG = "mainFragment"
    }
   private var _binding: FragmentMainBinding? = null
   private val binding get() = _binding!!
   private lateinit var dataStorePref: DataStorePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

   override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
       _binding = FragmentMainBinding.inflate(inflater, container, false)
       with(binding){
           btnTentangAplikasi.enableOnClickAnimation()
           btnDataPelanggan.enableOnClickAnimation()
           btnTentangAplikasi.setOnClickListener{
               findNavController().navigate(MainFragmentDirections.actionMainFragmentToTentangFragment())
           }
           btnDataPelanggan.setOnClickListener{
               findNavController().navigate(MainFragmentDirections.actionMainFragmentToDataPelangganFragment())
           }
       }


       return binding.root
   }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



    override fun onDestroyView() {
       super.onDestroyView()
       _binding = null
   }
}