package com.xnfl16.elaundrie.ui.data_pelanggan.detail

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.databinding.FragmentDialogDetailBinding
import com.xnfl16.elaundrie.utils.PdfFactory
import com.xnfl16.elaundrie.utils.enableOnClickAnimation
import java.io.File

class DialogDetailFragment : DialogFragment() {
    private var _binding: FragmentDialogDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DialogDetailFragmentArgs by navArgs()
    private val pdfFactory: PdfFactory by lazy{
        PdfFactory()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogDetailBinding.inflate(inflater, container, false)
        with(binding) {
            btnCetak.enableOnClickAnimation()
            btnSimpan.enableOnClickAnimation()
            val data = args.pelanggan
            val fileName = "ELaundrie ${data?.id}.pdf"
            tvId.text = getString(R.string.tv_id, data?.id)
            tvNama.text = getString(R.string.tv_nama, data?.nama)
            tvAlamat.text = getString(R.string.tv_alamat, data?.alamat)
            tvJumlah.text = getString(R.string.tv_jumlah, data?.jumlah)
            tvDiskon.text = getString(R.string.tv_diskon, data?.diskon + "%")
            tvStatus.text = getString(R.string.tv_status, data?.status)
            tvLayanan.text = getString(R.string.tv_layanan, data?.layanan)
            tvTotal.text = getString(R.string.tv_total, data?.total)
            tvTanggalDanWaktu.text = data?.tanggalDanWaktu

            btnSimpan.setOnClickListener {
                setupSendIntent()
            }

            Dexter.withContext(requireContext())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        btnCetak.setOnClickListener {
                            Log.d("DialogDetailFragment: ",data?.nama.toString())
                            Toast.makeText(context, data?.nama, Toast.LENGTH_SHORT).show()
                            pdfFactory.createPdfFile(requireActivity(),data,getAppPath() + fileName)
                        }
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {

                    }
                }).check()

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getAppPath(): String {
        val dir = File(
            requireActivity().applicationContext.getExternalFilesDir(null).toString()
                    + File.separator
                    + requireActivity().resources.getString(R.string.app_name)
                    + File.separator
        )
        if (!dir.exists())
            dir.mkdir()
        return dir.path + File.separator

    }

    private fun setupSendIntent() {
        val i = args.pelanggan
        val sendIntent = Intent(Intent.ACTION_SEND)
        val msg = getString(
            R.string.send_template,
            i?.tanggalDanWaktu,
            i?.id,
            i?.nama,
            i?.alamat,
            i?.jumlah,
            i?.diskon,
            i?.layanan,
            i?.status,
            i?.total
        )
        sendIntent.also {
            it.type = "text/plain"
            it.putExtra(Intent.EXTRA_TEXT, msg)
        }
        try {
            startActivity(sendIntent)
        } catch (e: Exception) {
            Log.e("DialogDetailFragment ", e.toString())
            return
        }
    }


}