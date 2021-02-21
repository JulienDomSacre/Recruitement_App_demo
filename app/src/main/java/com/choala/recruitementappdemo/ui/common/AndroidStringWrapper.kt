package com.choala.recruitementappdemo.ui.common

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.annotation.StringRes

class AndroidStringWrapper(
    @StringRes
    val key: Int,
    vararg val formatArgs: Any
) {
    internal fun getConcatenateString(
        context: Context
    ): String = getConcatenateString((context.resources))

    private fun getConcatenateString(
        resources: Resources
    ): String = try {
        resources.getString(key, *formatArgs)
    } catch (e: Exception) {
        Log.e(AndroidStringWrapper::class.java.simpleName, e.toString())
        ""
    }
}