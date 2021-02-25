package com.choala.recruitementappdemo.ui.common

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting

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

    @VisibleForTesting
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AndroidStringWrapper) return false

        if (key != other.key) return false
        if(!formatArgs.contentEquals(other.formatArgs)) return false

        return true
    }

    @VisibleForTesting
    override fun hashCode(): Int {
        var result = key
        result = 31 * result + formatArgs.contentHashCode()
        return result
    }
}