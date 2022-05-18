package com.xnfl16.elaundrie.ui.data_pelanggan

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.databinding.FragmentDataPelangganBinding

class DataPelangganFragment : Fragment() {
    private var _binding: FragmentDataPelangganBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetFragment: BottomSheetDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataPelangganBinding.inflate(inflater, container, false)
        with(binding){

        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSheetFragment = BottomSheetDialogFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
     }

    private fun setupListeners() {
        val binding: FragmentBo
        binding.btnAdd.setOnClickListener{
            bottomSheetFragment.show(childFragmentManager,"BottomSheetTambahPelanggan")

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}