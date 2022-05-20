

package com.xnfl16.elaundrie.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.databinding.FragmentCustomDialogNotificationBinding
import com.xnfl16.elaundrie.ui.data_pelanggan.DataPelangganAdapter
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("ClickableViewAccessibility")
fun View.enableOnClickAnimation() {
    setOnTouchListener { v, event ->
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val reducedvalue = 0.95F
            v?.scaleX = reducedvalue
            v?.scaleY = reducedvalue
        } else if (event?.action == MotionEvent.ACTION_UP) {
            v?.scaleX = 1f
            v?.scaleY = 1f
        }
        false
    }
}
@SuppressLint("ClickableViewAccessibility")
fun View.enableItemViewAnimation(alertDialog: DataPelangganAdapter) {
    setOnTouchListener { v, event ->
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val reducedvalue = 0.95F
            v?.scaleX = reducedvalue
            v?.scaleY = reducedvalue
        } else if (alertDialog.isShowing()) {
            v?.scaleX = 1f
            v?.scaleY = 1f
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