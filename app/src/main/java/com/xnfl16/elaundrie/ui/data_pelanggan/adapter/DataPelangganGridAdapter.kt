package com.xnfl16.elaundrie.ui.data_pelanggan.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.source.model.Pelanggan
import com.xnfl16.elaundrie.databinding.ItemLaundryGridBinding
import kotlin.collections.ArrayList

class DataPelangganGridAdapter(val ctx: Context): RecyclerView.Adapter<DataPelangganGridAdapter.ViewHolder>() {
    private lateinit var listener: DataPelangganListener
    private lateinit var builder: AlertDialog.Builder



    private var dataList = mutableListOf<Pelanggan>()
    private var dataFilterList: MutableList<Pelanggan> = ArrayList()

    init {
        dataFilterList = dataList
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<Pelanggan>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    fun setListener(itemViewListener: DataPelangganListener) {
        this.listener = itemViewListener
    }

    inner class ViewHolder(val binding: ItemLaundryGridBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemLaundryGridBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("LongLogTag")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = dataList[position]
//        holder.itemView.enableItemViewAnimation(this)
        holder.itemView.setOnClickListener {
            listener.onItemClick(dataList[position])
        }
        val statusResId = if(i.status == "Belum Bayar") ContextCompat.getColor(ctx,R.color.merah) else ContextCompat.getColor(ctx,R.color.hijau)
        with(holder.binding){
            nama.text = ctx.getString(R.string.nama_1_s,i.nama)
            tanggal.text = i.tanggalDanWaktu
            id.text = ctx.getString(R.string.id_1_s,i.id)
            status.text = i.status
            status.setTextColor(statusResId)
            jumlah.text = ctx.getString(R.string.jumlah_laundry,i.jumlah)
            layanan.text = i.layanan
            total.text = ctx.getString(R.string.total_1s,i.total)
        }

        holder.itemView.setOnLongClickListener { v ->

            builder = AlertDialog.Builder(ctx, R.style.AlertDialogTheme)
            val option = arrayOf("Update","Delete")
            builder.setItems(option
            ) { dialog, it ->
                if(it == 0){
                    listener.onItemLongClick(dataList[position],"Update")
                }
                if(it == 1){
                    listener.onItemLongClick(dataList[position],"Delete")
                }
            }.create().show()

            if (v != null) {
                Log.d("DataPelangganGridAdapter: ","${v.toString()} is clicked")
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}