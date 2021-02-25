package com.choala.recruitementappdemo.ui.common

data class UiScreen<out T>(val state: ViewState, val data: T?){
    companion object{
        fun <T> loading(data:T? = null): UiScreen<T> = UiScreen<T>(state = ViewState.LOADING, data = data)
        fun <T> success(data:T): UiScreen<T> = UiScreen<T>(state = ViewState.SUCCESS, data = data)
        fun <T> error(data:T? = null): UiScreen<T> = UiScreen<T>(state = ViewState.ERROR, data = data)
    }
}

enum class ViewState {
    LOADING,
    SUCCESS,
    ERROR
}
