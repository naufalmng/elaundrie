

package com.xnfl16.elaundrie.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.postDelayed
import com.google.android.material.snackbar.Snackbar
import com.xnfl16.elaundrie.R
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatImageView.setConnectivityStatus(context: Context, isConnected: Boolean){
    val resId = if(isConnected) R.color.hijau else R.color.merah
    backgroundTintList = AppCompatResources.getColorStateList(context,resId)
}

@SuppressLint("InflateParams")
fun showCustomSnackbar(ctx:Activity, v:View){
    val snackbar = Snackbar.make(v,"",Snackbar.LENGTH_SHORT)
    val customSnackbarView = ctx.layoutInflater.inflate(R.layout.item_info_gagal_lanjut,null)
    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
    val snackBarLayout = snackbar.view as Snackbar.SnackbarLayout
    snackBarLayout.setPadding(0,0,0,0)
    snackBarLayout.addView(customSnackbarView,0)
    snackbar.show()
    if(snackbar.isShown){
        Handler(Looper.getMainLooper()).postDelayed({
           snackbar.dismiss()
        },2500)
    }
}
@SuppressLint("ClickableViewAccessibility")
fun View.enableOnClickAnimation() {
    val reducedvalue = 0.95F
    val defaultValue = 1.0f
    setOnTouchListener { v, event ->
        when(event.action){
            MotionEvent.ACTION_UP -> animate().scaleX(defaultValue).scaleY(defaultValue).duration = 0
            MotionEvent.ACTION_DOWN -> animate().scaleX(reducedvalue).scaleY(reducedvalue).duration = 0
            MotionEvent.ACTION_CANCEL -> animate().scaleX(defaultValue).scaleY(defaultValue).duration = 0
        }
        false
    }
}


@SuppressLint("ShowToast")
fun Activity.showToast(title: String){
    val toast: Toast = Toast.makeText(this,title,Toast.LENGTH_SHORT)
    toast.show()
}

fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss aa ", Locale.getDefault())
    val c = Calendar.getInstance().getTime();
    return formatter.format(c)
}
//fun Activity.snackbar(v: View,title: String){

//    val snackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT)
//    val customSnackView: View = layoutInflater.inflate(R.layout.fragment_custom_dialog_notification, null)
//    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
//    val snackbarLayout = snackbar.view as SnackbarLayout
//    snackbarLayout.setPadding(0, 0, 0, 0)
//    bind.title.text = title
//
//    // add the custom snack bar layout to snackbar layout
//    snackbarLayout.addView(customSnackView, 0)
//    snackbar.show()
//}