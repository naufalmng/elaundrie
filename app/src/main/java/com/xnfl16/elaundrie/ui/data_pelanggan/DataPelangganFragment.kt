package com.xnfl16.elaundrie.ui.data_pelanggan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.model.Pelanggan
import com.xnfl16.elaundrie.databinding.FragmentDataPelangganBinding
import com.xnfl16.elaundrie.utils.LoadingDialog
import com.xnfl16.elaundrie.utils.enableOnClickAnimation
import com.xnfl16.elaundrie.utils.showToast


class DataPelangganFragment : Fragment() {
    companion object {
        const val TAG = "DataPelangganFragment: "
    }

    private var _binding: FragmentDataPelangganBinding? = null
    private val binding get() = _binding!!

//    private lateinit var dialogBinding: FragmentBottomSheetInsertBinding

    private lateinit var dialogTambahPelanggan: DialogTambahPelanggan

    private val loading: LoadingDialog by lazy {
        LoadingDialog(requireActivity())
    }

    private val viewModel: DataPelangganViewModel by lazy {
        ViewModelProvider(this)[DataPelangganViewModel::class.java]
    }

    private lateinit var dataPelangganAdapter: DataPelangganAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataPelangganBinding.inflate(inflater, container, false)
        with(binding) {

        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataPelangganAdapter = DataPelangganAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
        setupRecyclerView()
        setupOnItemClick()
    }



    private fun setupOnItemClick() {
        dataPelangganAdapter.setListener(object : DataPelangganListener {
            override fun onItemClick(it: Pelanggan) {
                Toast.makeText(requireContext(), "${it.nama}", Toast.LENGTH_SHORT).show()
            }

            override fun onItemLongClick(it: Pelanggan,isUpdateOrDelete: String) {
                if(isUpdateOrDelete == "Update"){
                    findNavController().navigate(DataPelangganFragmentDirections.actionDataPelangganFragmentToDialogUpdateFragment(it))
                }
            }
        })
    }


    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = dataPelangganAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        viewModel.isInsertSuccess.observe(viewLifecycleOwner) {

            if (it != true) {
                loading.start()
            } else {
                loading.dismiss()
                requireActivity().showToast(getString(R.string.data_berhasil_ditambah))
            }
        }

        viewModel.isUpdateSuccess.observe(viewLifecycleOwner){
            if (it != true) {
                loading.start()
            } else {
                loading.dismiss()
                requireActivity().showToast(getString(R.string.data_berhasil_diupdate))
            }
        }

        viewModel.dataPelanggan.observe(viewLifecycleOwner) {
            if (it != null) {
                dataPelangganAdapter.setData(it)
            } else Toast.makeText(requireContext(), "Gagal mendapatkan data", Toast.LENGTH_SHORT)
                .show()
        }


    }

    private fun setupListeners() {
        binding.btnAdd.enableOnClickAnimation()


        binding.btnAdd.setOnClickListener {
            dialogTambahPelanggan = DialogTambahPelanggan(this,requireActivity(),viewModel)
            dialogTambahPelanggan.show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
