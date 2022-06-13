package com.xnfl16.elaundrie.ui.data_pelanggan.update

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.source.model.Pelanggan
import com.xnfl16.elaundrie.core.data.source.network.State
import com.xnfl16.elaundrie.databinding.FragmentDialogUpdateBinding
import com.xnfl16.elaundrie.ui.data_pelanggan.DataPelangganViewModel
import com.xnfl16.elaundrie.ui.data_pelanggan.adapter.DataPelangganAdapter
import com.xnfl16.elaundrie.utils.LoadingDialog
import com.xnfl16.elaundrie.utils.enableOnClickAnimation
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
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
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
        setupObservers()
        setupListeners()
     }

    private fun setupListeners() {
        showData()
        binding.spinnerLayanan.lifecycleOwner = viewLifecycleOwner
        binding.spinnerStatus.lifecycleOwner = viewLifecycleOwner
        binding.btnUpdate.enableOnClickAnimation()
        binding.btnUpdate.setOnClickListener{
            if(isFormValid()){
                val nama = binding.etNama.text
                Log.d("DialogUpdateFragment: ",nama.toString())
                val alamat = binding.etAlamat.text
                val diskon = binding.etDiskon.text
                val jumlah = binding.etJumlah.text
                var status = if(binding.spinnerStatus.selectedIndex == 1) "Belum Bayar" else "Lunas"
                val layanan = when(binding.spinnerLayanan.selectedIndex){
                    0 -> "Flash 1 Hour"
                    1 -> "Express 1 Day"
                    else -> "Regular 2 Day"
                }

                val data = Pelanggan(
                    args.dataLaundryPelanggan?.id,
                    nama.toString(),
                    alamat.toString(),
                    args.dataLaundryPelanggan?.tanggalDanWaktu,
                    jumlah.toString(),
                    diskon.toString(),
                    args.dataLaundryPelanggan?.total,
                    status,
                    layanan
                )
                Log.d("DialogUpdateFragment: ",data.toString())

                binding.spinnerStatus.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
                    status = text
                }
                viewModel.updateData(data)
            }
//            Log.d("DialogUpdateFragment: ",binding.etDiskon.text)

        }


        binding.btnClose.setOnClickListener{
            findNavController().navigate(R.id.action_dialogUpdateFragment_to_dataPelangganFragment)
        }
    }

    private fun setupObservers() {
        viewModel.isUpdateSuccess.observe(viewLifecycleOwner){
            if (it != true) {
                loading.start(State.SUCCESS)
            } else {
                loading.dismiss()
                requireActivity().showToast(getString(R.string.data_berhasil_diupdate))
                dismiss()
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
        val selectedStatus = if(args.dataLaundryPelanggan?.status == "Belum Bayar") 1 else 0
        val selectedLayanan = when(args.dataLaundryPelanggan?.status){
            "Flash 1 Hour" -> 0
            "Express 1 Day" -> 1
            else -> 2
        }

        binding.etNama.setText(args.dataLaundryPelanggan?.nama)
        binding.etAlamat.setText(args.dataLaundryPelanggan?.alamat)
        binding.etDiskon.setText(args.dataLaundryPelanggan?.diskon)
        binding.etJumlah.setText(args.dataLaundryPelanggan?.jumlah)
        binding.spinnerStatus.selectItemByIndex(selectedStatus)
        binding.spinnerLayanan.selectItemByIndex(selectedLayanan)
        if(binding.spinnerStatus.selectedIndex != -1){
            binding.tilStatus.hint =  getString(R.string.status_pembayaran)
        }
        if(binding.spinnerLayanan.selectedIndex != -1){
            binding.tilLayanan.hint =  getString(R.string.pilih_layanan)
        }

        binding.spinnerStatus.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            binding.tilStatus.hint =  getString(R.string.status_pembayaran)
        }
        binding.spinnerLayanan.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            binding.tilLayanan.hint =  getString(R.string.pilih_layanan)
        }
    }

    private fun isFormValid(): Boolean {
        val selectedStatus = binding.spinnerStatus.selectedIndex
        val selectedLayanan = binding.spinnerLayanan.selectedIndex

        if (TextUtils.isEmpty(binding.etNama.text)) {
            Toast.makeText(requireContext(), "Nama tidak boleh kosong !", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(binding.etJumlah.text)) {
            Toast.makeText(requireContext(), "Jumlah tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (TextUtils.isEmpty(binding.etDiskon.text)) {
            Toast.makeText(requireContext(), "Diskon tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (TextUtils.isEmpty(binding.etAlamat.text)) {
            Toast.makeText(requireContext(), "Alamat tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (selectedStatus == -1) {
            Toast.makeText(
                requireContext(),
                "Status pembayaran tidak boleh kosong !",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (selectedLayanan == -1) {
            Toast.makeText(
                requireContext(),
                "Layanan tidak boleh kosong !",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}