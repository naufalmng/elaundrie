package com.xnfl16.elaundrie.ui.tentang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.DataStorePreferences
import com.xnfl16.elaundrie.core.data.dataStore
import com.xnfl16.elaundrie.databinding.FragmentTentangBinding
import com.xnfl16.elaundrie.ui.main.MainFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TentangFragment : Fragment() {
    companion object{
        const val TAG = "tentangFragment"
    }
    private var _binding: FragmentTentangBinding? = null
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
        _binding = FragmentTentangBinding.inflate(inflater, container, false)
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