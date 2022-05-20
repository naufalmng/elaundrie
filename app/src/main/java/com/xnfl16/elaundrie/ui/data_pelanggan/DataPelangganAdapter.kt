package com.xnfl16.elaundrie.ui.data_pelanggan

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.model.Pelanggan
import com.xnfl16.elaundrie.databinding.ItemLaundryBinding
import com.xnfl16.elaundrie.utils.enableItemViewAnimation
import com.xnfl16.elaundrie.utils.enableOnClickAnimation
import com.xnfl16.elaundrie.utils.getCurrentTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DataPelangganAdapter(val ctx: Context): RecyclerView.Adapter<DataPelangganAdapter.ViewHolder>() {

    private lateinit var listener: DataPelangganListener
    private lateinit var builder: AlertDialog.Builder


    private var dataList = ArrayList<Pelanggan>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<Pelanggan>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setListener(itemViewListener: DataPelangganListener) {
        this.listener = itemViewListener
    }

    inner class ViewHolder(val binding: ItemLaundryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataPelangganAdapter.ViewHolder {
        return ViewHolder(ItemLaundryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: DataPelangganAdapter.ViewHolder, position: Int) {
        val i = dataList[position]
//        holder.itemView.enableItemViewAnimation(this)
        holder.itemView.setOnClickListener {
            listener.onItemClick(dataList[position])
        }

        with(holder.binding){
            nama.text = i.nama
            tanggal.text = i.tanggalDanWaktu
            id.text = ctx.getString(R.string.id_1_s,i.id)
            status.text = i.status
            jumlah.text = ctx.getString(R.string.jumlah_laundry,i.jumlah)
            total.text = ctx.getString(R.string.total_1s,i.total)
        }

        holder.itemView.setOnLongClickListener { v ->

            builder = AlertDialog.Builder(ctx,R.style.AlertDialogTheme)
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

            }
            true
        }
    }

    fun isShowing(): Boolean{
        if(builder.create().isShowing){
            return true
        }
        return false
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

interface DataPelangganListener{
    fun onItemClick(it: Pelanggan)
    fun onItemLongClick(it: Pelanggan,isUpdateOrDelete: String)
}


