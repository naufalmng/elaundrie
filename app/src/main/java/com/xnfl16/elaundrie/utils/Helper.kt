package com.xnfl16.elaundrie.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

@SuppressLint("ClickableViewAccessibility")
fun View.enableOnClickAnimation() {
    setOnTouchListener { v, event ->
        if (event?.action == MotionEvent.ACTION_DOWN) {
            // scale your value
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