package com.area.freecture.listeners


interface ResponseListener {
    fun onSuccess(`object`: String)
    fun onError(message: String)
}
