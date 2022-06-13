package com.xnfl16.elaundrie.ui.data_pelanggan.tambah

import android.app.Activity
import android.text.TextUtils
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.databinding.FragmentBottomSheetInsertBinding
import com.xnfl16.elaundrie.ui.data_pelanggan.DataPelangganViewModel
import com.xnfl16.elaundrie.utils.enableOnClickAnimation

class DialogTambahPelanggan(private val fm: Fragment, private val activity: Activity, private val viewModel: DataPelangganViewModel) {

    private lateinit var dialogTambahBinding: FragmentBottomSheetInsertBinding
    private lateinit var dialog: BottomSheetDialog
    fun dismiss(){
        dialog.dismiss()
    }
    fun show(){

        dialogTambahBinding = FragmentBottomSheetInsertBinding.inflate(activity.layoutInflater, null, false)
        dialogTambahBinding.btnTambah.enableOnClickAnimation()
        dialogTambahBinding.spinnerStatus.lifecycleOwner = fm.viewLifecycleOwner
        dialogTambahBinding.spinnerLayanan.lifecycleOwner = fm.viewLifecycleOwner
        dialog = BottomSheetDialog(fm.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogTambahBinding.root)
        dialog.show()

        var status: String? = null
        var layanan: String? = null

        dialogTambahBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialogTambahBinding.spinnerStatus.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            status = text
            dialogTambahBinding.tilStatus.hint =  fm.getString(R.string.status_pembayaran)
        }
        dialogTambahBinding.spinnerLayanan.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            layanan = text
            dialogTambahBinding.tilLayanan.hint =  fm.getString(R.string.pilih_layanan)
        }

        dialogTambahBinding.btnTambah.setOnClickListener {
            if(isFormValid()){
                val nama = dialogTambahBinding.etNama.text.toString()
                val jumlah = dialogTambahBinding.etJumlah.text.toString().toDouble()
                val diskon = dialogTambahBinding.etDiskon.text.toString().toDouble()
                val alamat = dialogTambahBinding.etAlamat.text.toString()
                viewModel.insertData(nama, alamat, jumlah, status!!, diskon, layanan!!)
            }
        }
        dialog.setOnDismissListener {
            with(dialogTambahBinding) {
                etNama.text?.clear()
                etJumlah.text?.clear()
                etDiskon.text?.clear()
                etAlamat.text?.clear()
            }
        }
    }


    private fun isFormValid(): Boolean {
        val selectedStatus = dialogTambahBinding.spinnerStatus.selectedIndex
        val selectedLayanan = dialogTambahBinding.spinnerLayanan.selectedIndex

        if (TextUtils.isEmpty(dialogTambahBinding.etNama.text)) {
            Toast.makeText(fm.requireContext(), "Nama tidak boleh kosong !", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(dialogTambahBinding.etJumlah.text)) {
            Toast.makeText(fm.requireContext(), "Jumlah tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (TextUtils.isEmpty(dialogTambahBinding.etDiskon.text)) {
            Toast.makeText(fm.requireContext(), "Diskon tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (TextUtils.isEmpty(dialogTambahBinding.etAlamat.text)) {
            Toast.makeText(fm.requireContext(), "Alamat tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (selectedStatus == -1) {
            Toast.makeText(
                fm.requireContext(),
                "Status pembayaran tidak boleh kosong !",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (selectedLayanan == -1) {
            Toast.makeText(
                fm.requireContext(),
                "Layanan tidak boleh kosong !",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }
    
}