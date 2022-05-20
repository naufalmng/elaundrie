package com.xnfl16.elaundrie.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.databinding.FragmentCustomDialogNotificationBinding

class NotificationDialog(private val activity: Activity) {
    private lateinit var dialog: AlertDialog
    private var _bind: FragmentCustomDialogNotificationBinding? = null
    val bind get() = (_bind)


    fun setTitle(title: String){
        _bind = FragmentCustomDialogNotificationBinding.inflate(LayoutInflater.from(activity.applicationContext),null,false)
        bind?.title?.text = title
    }


    fun show(){
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_custom_dialog_notification,null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(true)
        val window = dialog.window
        val wlp = window?.attributes
        wlp?.gravity = Gravity.BOTTOM
        wlp?.flags = wlp!!.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp
        builder.setView(dialogView)
        builder.setCancelable(true)
        dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            _bind = null
        },1500)

//        dialog.window?.setLayout(450,400)
    }
}