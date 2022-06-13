package com.xnfl16.elaundrie.ui.tentang

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.DataStorePreferences
import com.xnfl16.elaundrie.core.data.dataStore
import com.xnfl16.elaundrie.core.data.repository.AppRepository
import com.xnfl16.elaundrie.core.data.source.RemoteDataSource
import com.xnfl16.elaundrie.core.data.source.network.ApiConfig
import com.xnfl16.elaundrie.core.data.source.network.State
import com.xnfl16.elaundrie.databinding.FragmentTentangBinding
import com.xnfl16.elaundrie.ui.main.MainFragment
import com.xnfl16.elaundrie.utils.LoadingDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TentangFragment : Fragment() {
    companion object{
        const val TAG = "tentangFragment"
    }
    private var _binding: FragmentTentangBinding? = null
    private val binding get() = _binding!!
    private val viewModel : TentangViewModel by lazy {
        val api = ApiConfig.provideApiService
        val remoteDataSource = RemoteDataSource(api)
        val db = AppRepository(remoteDataSource)
        val factory = TentangVIewModelFactory(db)
        ViewModelProvider(this,factory)[TentangViewModel::class.java]
    }
    private val loading: LoadingDialog by lazy {
        LoadingDialog(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initCopyrightString()
        loading.start(State.LOADING)

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
        setupObserver()
     }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setupObserver() {
        viewModel.copyrightString.observe(this@TentangFragment){
            if(it!=null){
                binding.textView.text = it.toString()
                loading.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}