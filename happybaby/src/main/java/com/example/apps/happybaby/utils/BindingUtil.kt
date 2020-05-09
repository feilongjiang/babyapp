package com.example.apps.happybaby.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("heightEqWidth")
fun bindWEqH(view: View,id:Long) {
    var lp = view.layoutParams
    lp.height = view.width
}