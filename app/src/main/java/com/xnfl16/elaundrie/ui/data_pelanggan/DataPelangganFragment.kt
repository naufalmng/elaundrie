package com.xnfl16.elaundrie.ui.data_pelanggan

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.DataStorePreferences
import com.xnfl16.elaundrie.core.data.dataStore
import com.xnfl16.elaundrie.core.data.source.model.Pelanggan
import com.xnfl16.elaundrie.core.data.source.network.State
import com.xnfl16.elaundrie.databinding.FragmentDataPelangganBinding
import com.xnfl16.elaundrie.ui.data_pelanggan.adapter.DataPelangganAdapter
import com.xnfl16.elaundrie.ui.data_pelanggan.adapter.DataPelangganGridAdapter
import com.xnfl16.elaundrie.ui.data_pelanggan.adapter.DataPelangganListener
import com.xnfl16.elaundrie.ui.data_pelanggan.tambah.DialogTambahPelanggan
import com.xnfl16.elaundrie.utils.LoadingDialog
import com.xnfl16.elaundrie.utils.enableOnClickAnimation
import com.xnfl16.elaundrie.utils.showToast
import kotlinx.coroutines.launch


class DataPelangganFragment : Fragment() {
    companion object {
        const val TAG = "dataPelangganFragment"
    }

    private var _binding: FragmentDataPelangganBinding? = null
    private val binding get() = _binding!!
    private var isLinearLayoutManager = true
    private lateinit var dataStorePref: DataStorePreferences

    private lateinit var dialogTambahPelanggan: DialogTambahPelanggan

    private val loading: LoadingDialog by lazy {
        LoadingDialog(requireActivity())
    }

    private val viewModel: DataPelangganViewModel by lazy {
        ViewModelProvider(this)[DataPelangganViewModel::class.java]
    }

    private lateinit var dataPelangganAdapter: DataPelangganAdapter
    private lateinit var dataPelangganGridAdapter: DataPelangganGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataPelangganBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataPelangganAdapter = DataPelangganAdapter(requireContext())
        dataPelangganGridAdapter = DataPelangganGridAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.isRefreshing = true
        if(binding.swipeRefreshLayout.isRefreshing){
            loading.start(State.LOADING)
        }else loading.dismiss()

        setupListeners()
        setupObservers()
        setupRecyclerView()
        setupOnItemClick()
        setupDataStorePref()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        val layoutSwitcher = menu.findItem(R.id.action_switch_layout)
        setLayoutSwitcherIcon(layoutSwitcher)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                isLinearLayoutManager = !isLinearLayoutManager
                lifecycleScope.launch {
                    dataStorePref.saveLayoutSetting(requireContext(), isLinearLayoutManager)
                }
                setupLayoutSwitcher()
                setLayoutSwitcherIcon(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLayoutSwitcherIcon(menuItem: MenuItem?) {
        if (menuItem == null) return

        menuItem.icon = if (isLinearLayoutManager) ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_linear_layout
        )
        else ContextCompat.getDrawable(requireContext(), R.drawable.ic_grid_layout)
    }

    private fun setupLayoutSwitcher() {
        if (isLinearLayoutManager) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.recyclerViewGrid.visibility = View.GONE
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.recyclerViewGrid.visibility = View.VISIBLE
        }
    }


    private fun setupDataStorePref() {
        dataStorePref = DataStorePreferences(requireContext().dataStore)
        dataStorePref.preferenceFlow.asLiveData()
            .observe(viewLifecycleOwner) {
                isLinearLayoutManager = it
                setupLayoutSwitcher()
                activity?.invalidateOptionsMenu()
            }
    }


    private fun setupOnItemClick() {
        dataPelangganAdapter.setListener(object : DataPelangganListener {
            override fun onItemClick(it: Pelanggan) {
                findNavController().navigate(
                    DataPelangganFragmentDirections.actionDataPelangganFragmentToDialogDetailFragment(
                        it
                    )
                )
            }

            override fun onItemLongClick(it: Pelanggan, isUpdateOrDelete: String) {
                if (isUpdateOrDelete == "Update") {
                    findNavController().navigate(
                        DataPelangganFragmentDirections.actionDataPelangganFragmentToDialogUpdateFragment(
                            it
                        )
                    )
                } else {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(),R.style.AlertDialogTheme)
                    builder.setTitle("Hapus data ${it.nama} ?")
                    builder.setNegativeButton("Tidak") { dialog, i ->
                        dialog.dismiss()
                    }
                    builder.setPositiveButton("Ya") { dialog, i ->
                        viewModel.deleteData(it.id.toString())
                        dialog.dismiss()
                    }.create().show()
                }
            }
        })
        dataPelangganGridAdapter.setListener(object : DataPelangganListener {
            override fun onItemClick(it: Pelanggan) {
                findNavController().navigate(
                    DataPelangganFragmentDirections.actionDataPelangganFragmentToDialogDetailFragment(
                        it
                    )
                )
            }

            override fun onItemLongClick(it: Pelanggan, isUpdateOrDelete: String) {
                if (isUpdateOrDelete == "Update") {
                    findNavController().navigate(
                        DataPelangganFragmentDirections.actionDataPelangganFragmentToDialogUpdateFragment(
                            it
                        )
                    )
                } else viewModel.deleteData(it.id.toString())
            }
        })
    }


    private fun setupRecyclerView() {
        with(binding) {
            recyclerView.adapter = dataPelangganAdapter
            recyclerView.setHasFixedSize(true)

            recyclerViewGrid.adapter = dataPelangganGridAdapter
            recyclerViewGrid.setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        viewModel.isInsertSuccess.observe(viewLifecycleOwner) {

            if (it != true) {
                loading.start(State.SUCCESS)
            } else {
                loading.dismiss()
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(getString(R.string.data_berhasil_ditambah))
            }
        }

        viewModel.isDeleteSuccess.observe(viewLifecycleOwner) {
            if (it != true) {
                loading.start(State.SUCCESS)
            } else {
                loading.dismiss()
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(getString(R.string.data_berhasil_dihapus))
            }
        }

        viewModel.errorMsg.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.swipeRefreshLayout.isRefreshing = false
                loading.dismiss()
                Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isUpdateSuccess.observe(viewLifecycleOwner) {
            if (it != true) {
                loading.start(State.SUCCESS)
            } else {
                loading.dismiss()
                binding.swipeRefreshLayout.isRefreshing = false
                requireActivity().showToast(getString(R.string.data_berhasil_diupdate))
            }
        }

        viewModel.isInsertSuccess.observe(viewLifecycleOwner){
            if(it==true){
                binding.swipeRefreshLayout.isRefreshing = false
                dialogTambahPelanggan.dismiss()
                loading.dismiss()
            }
        }

        viewModel.dataPelanggan.observe(viewLifecycleOwner) {
            if (it != null) {
                dataPelangganAdapter.setData(it)
                dataPelangganGridAdapter.setData(it)
                binding.rvLayout.visibility = View.VISIBLE
                binding.swipeRefreshLayout.isRefreshing = false
                loading.dismiss()
            } else {
                Toast.makeText(requireContext(), "Gagal mendapatkan data", Toast.LENGTH_SHORT)
                    .show()
                binding.swipeRefreshLayout.isRefreshing = false
                loading.dismiss()
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setupListeners() {

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.rvLayout.visibility = View.GONE
            viewModel.initDataPelanggan()
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0.toString() == ""){
                    dataPelangganAdapter.setData(viewModel.getCurrentData(""))
                    dataPelangganGridAdapter.setData(viewModel.getCurrentData(""))
                }else {
                    viewModel.searchData(requireContext(),p0.toString())
                }
                return false
            }
        })
        binding.btnAdd.enableOnClickAnimation()
        binding.btnRemove.enableOnClickAnimation()
        binding.btnRemove.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(),R.style.AlertDialogTheme)
            builder.setTitle("Hapus semua data ?")
            builder.setNegativeButton("Tidak") { dialog, i ->
                dialog.dismiss()
            }
            builder.setPositiveButton("Ya") { dialog, i ->
                viewModel.deleteAllData()
                dialog.dismiss()
            }.create().show()
        }

        binding.btnAdd.setOnClickListener {
            dialogTambahPelanggan = DialogTambahPelanggan(this, requireActivity(), viewModel)
            dialogTambahPelanggan.show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
