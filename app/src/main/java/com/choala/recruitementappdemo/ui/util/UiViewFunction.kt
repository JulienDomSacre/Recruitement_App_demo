package com.choala.recruitementappdemo.ui.util

import android.view.View

fun View.visibilityIsRequested(visible :Boolean){
    this.visibility = if(visible) View.VISIBLE else View.GONE
}