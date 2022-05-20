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
import androidx.navigation.fragment.findNavController
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.databinding.FragmentMainBinding
import com.xnfl16.elaundrie.utils.enableOnClickAnimation
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {
   private var _binding: FragmentMainBinding? = null
   private val binding get() = _binding!!

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