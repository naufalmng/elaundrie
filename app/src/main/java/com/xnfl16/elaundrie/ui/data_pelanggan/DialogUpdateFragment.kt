package com.xnfl16.elaundrie.ui.data_pelanggan

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.databinding.FragmentDialogUpdateBinding
import com.xnfl16.elaundrie.utils.LoadingDialog
import com.xnfl16.elaundrie.utils.showToast

class DialogUpdateFragment: DialogFragment() {
    private var _binding: FragmentDialogUpdateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DataPelangganViewModel by lazy {
        ViewModelProvider(this)[DataPelangganViewModel::class.java]
    }
    private val loading: LoadingDialog by lazy {
        LoadingDialog(requireActivity())
    }

    private lateinit var dataPelangganAdapter: DataPelangganAdapter

    private val args: DialogUpdateFragmentArgs by navArgs()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataPelangganAdapter = DataPelangganAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showData()
        setupObservers()
     }

    private fun setupObservers() {
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

    private fun showData() {
        val selectedIndex = if(args.dataLaundryPelanggan?.status == "Belum Bayar") 1 else 0

        binding.btnClose.setOnClickListener{
            findNavController().navigate(R.id.action_dialogUpdateFragment_to_dataPelangganFragment)
        }
        binding.etNama.setText(args.dataLaundryPelanggan?.nama)
        binding.etAlamat.setText(args.dataLaundryPelanggan?.alamat)
        binding.etDiskon.setText(args.dataLaundryPelanggan?.diskon)
        binding.etJumlah.setText(args.dataLaundryPelanggan?.jumlah)
        binding.spinnerStatus.selectItemByIndex(selectedIndex)
        if(binding.spinnerStatus.selectedIndex != -1){
            binding.tilSpinner.hint =  getString(R.string.status_pembayaran)
        }
        binding.spinnerStatus.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            binding.tilSpinner.hint =  getString(R.string.status_pembayaran)
        }
        binding.btnUpdate.setOnClickListener{

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}