package com.xnfl16.elaundrie.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog

import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.source.network.State

class LoadingDialog(private val activity: Activity) {
    lateinit var progressDialog: AlertDialog
    fun start(state: State){
        val w = if(state == State.FAILED) WindowManager.LayoutParams.MATCH_PARENT else WindowManager.LayoutParams.WRAP_CONTENT
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
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.show()
        progressDialog.window?.setLayout(w,WindowManager.LayoutParams.WRAP_CONTENT)
        progressDialog.window?.setGravity(Gravity.CENTER)
        val lp = progressDialog.window?.attributes
        lp?.dimAmount = 0.7f
        lp?.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        progressDialog.window?.attributes = lp


        if(state == State.SUCCESS) progressDialog.dismiss()
    }

    fun dismiss(){
        progressDialog.dismiss()
    }
}