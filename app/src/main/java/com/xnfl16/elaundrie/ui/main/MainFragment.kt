package com.xnfl16.elaundrie.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.source.network.State
import com.xnfl16.elaundrie.databinding.FragmentMainBinding
import com.xnfl16.elaundrie.utils.*
import kotlinx.coroutines.DelicateCoroutinesApi
@SuppressLint("FragmentLiveDataObserve")
@DelicateCoroutinesApi
class MainFragment : Fragment() {
    companion object {
        const val TAG = "mainFragment"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var loading: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainViewModel()
        loading = LoadingDialog(requireActivity())
        loading.start(State.LOADING)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        with(binding) {

        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupCarousel()
    }

    private fun setupObservers() {
        viewModel.isConnected.observe(this@MainFragment){isConnected->
            with(binding){
                if(isConnected==true){
                    btnTentangAplikasi.enableOnClickAnimation()
                    btnDataPelanggan.enableOnClickAnimation()
                    btnTentangAplikasi.setOnClickListener {
                        findNavController().navigate(MainFragmentDirections.actionMainFragmentToTentangFragment())
                    }
                    btnDataPelanggan.setOnClickListener {
                        findNavController().navigate(MainFragmentDirections.actionMainFragmentToDataPelangganFragment())
                    }
                }else {
                    btnTentangAplikasi.setOnClickListener {
                       showCustomSnackbar(requireActivity(),binding.btnDataPelanggan)

                    }
                    btnDataPelanggan.setOnClickListener {
                        showCustomSnackbar(requireActivity(),binding.btnDataPelanggan)
                    }
                    return@observe
                }
            }

        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setupCarousel() {
        val networkConnection = NetworkConnectivity(requireActivity().applicationContext)
        networkConnection.observe(this@MainFragment) { isConnected ->
            if (loading.progressDialog.isShowing) {
                loading.dismiss()
            }
            if (isConnected) {
                binding.networkIndicator.setConnectivityStatus(requireContext(),true)
                viewModel.connectionSuccess()
                binding.carouselView.apply {
                    registerLifecycle(requireActivity().lifecycle)
                    setData(viewModel.imgUrl)
                    start()
                    loading.dismiss()
                }
            } else {
                binding.networkIndicator.setConnectivityStatus(requireContext(),false)
                binding.carouselView.carouselBackground = AppCompatResources.getDrawable(requireContext(),R.mipmap.ic_launcher)
                viewModel.connectionFailed()
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}