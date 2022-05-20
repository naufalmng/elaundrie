package com.xnfl16.elaundrie.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog

import com.xnfl16.elaundrie.R

class LoadingDialog(private val activity: Activity) {
    private lateinit var progressDialog: AlertDialog
    fun start(){
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item,null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        progressDialog = builder.create()
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        progressDialog.show()
        progressDialog.window?.setLayout(450,400)
    }

    fun dismiss(){
        progressDialog.dismiss()
    }
}