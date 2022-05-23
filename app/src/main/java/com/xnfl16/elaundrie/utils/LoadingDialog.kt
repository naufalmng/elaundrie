package com.xnfl16.elaundrie.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog

import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.network.State

class LoadingDialog(private val activity: Activity) {
    lateinit var progressDialog: AlertDialog
    fun start(state: State){
        val w = if(state == State.FAILED) 1000 else 450
        showDialog(state,w)
    }

    private fun showDialog(state: State,w: Int){
        val layout = when(state){
            State.FAILED -> R.layout.item_loading_failed
            else -> R.layout.item_loading
        }
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(layout,null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        progressDialog = builder.create()
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        progressDialog.show()
        progressDialog.window?.setLayout(w,450)
        if(state == State.SUCCESS) progressDialog.dismiss()
    }

    fun dismiss(){
        progressDialog.dismiss()
    }
}