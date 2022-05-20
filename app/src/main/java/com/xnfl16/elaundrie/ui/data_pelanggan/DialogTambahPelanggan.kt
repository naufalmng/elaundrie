package com.xnfl16.elaundrie.ui.data_pelanggan

import android.app.Activity
import android.text.TextUtils
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.databinding.FragmentBottomSheetInsertBinding
import com.xnfl16.elaundrie.utils.enableOnClickAnimation

class DialogTambahPelanggan(private val fm: Fragment, private val activity: Activity, private val viewModel: DataPelangganViewModel) {

    private lateinit var dialogTambahBinding: FragmentBottomSheetInsertBinding

    fun show(){

        dialogTambahBinding = FragmentBottomSheetInsertBinding.inflate(activity.layoutInflater, null, false)
        dialogTambahBinding.btnTambah.enableOnClickAnimation()
        dialogTambahBinding.spinnerStatus.lifecycleOwner = fm.viewLifecycleOwner
        val dialog = BottomSheetDialog(fm.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogTambahBinding.root)
        dialog.show()

        var status: String? = null

        dialogTambahBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialogTambahBinding.spinnerStatus.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            status = text
            dialogTambahBinding.tilSpinner.hint =  fm.getString(R.string.status_pembayaran)
        }
        dialogTambahBinding.btnTambah.setOnClickListener {
            isFormValid()
            val nama = dialogTambahBinding.etNama.text.toString()
            val jumlah = dialogTambahBinding.etJumlah.text.toString().toDouble()
            val diskon = dialogTambahBinding.etDiskon.text.toString().toDouble()
            val alamat = dialogTambahBinding.etAlamat.text.toString()
            val layanan = dialogTambahBinding.etLayanan.text.toString()
            viewModel.insertData(nama, alamat, jumlah, status!!, diskon, layanan)
        }
        dialog.setOnDismissListener {
            with(dialogTambahBinding) {
                etNama.text?.clear()
                etJumlah.text?.clear()
                etDiskon.text?.clear()
                etAlamat.text?.clear()
                etLayanan.text?.clear()
            }
        }
    }


    private fun isFormValid() {
        val selectedIndex = dialogTambahBinding.spinnerStatus.selectedIndex

        if (TextUtils.isEmpty(dialogTambahBinding.etNama.text)) {
            Toast.makeText(fm.requireContext(), "Nama tidak boleh kosong !", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(dialogTambahBinding.etJumlah.text)) {
            Toast.makeText(fm.requireContext(), "Jumlah tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (TextUtils.isEmpty(dialogTambahBinding.etDiskon.text)) {
            Toast.makeText(fm.requireContext(), "Diskon tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (TextUtils.isEmpty(dialogTambahBinding.etAlamat.text)) {
            Toast.makeText(fm.requireContext(), "Alamat tidak boleh kosong !", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (selectedIndex == -1) {
            Toast.makeText(
                fm.requireContext(),
                "Status pembayaran tidak boleh kosong !",
                Toast.LENGTH_SHORT
            ).show()
            return
        }


    }
    
}