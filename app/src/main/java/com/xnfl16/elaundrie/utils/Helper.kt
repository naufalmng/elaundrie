

package com.xnfl16.elaundrie.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


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