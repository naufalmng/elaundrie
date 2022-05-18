package com.xnfl16.elaundrie.ui.tentang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.databinding.FragmentTentangBinding

class TentangFragment : Fragment() {
    private var _binding: FragmentTentangBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTentangBinding.inflate(inflater, container, false)
        binding.appBar.title.text = getString(R.string.tentang_fragment)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_tentangFragment_to_mainFragment)
        }
     }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}