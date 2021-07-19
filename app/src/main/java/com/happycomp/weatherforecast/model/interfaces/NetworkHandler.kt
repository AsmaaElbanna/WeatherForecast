package com.happycomp.weatherforecast.model.interfaces

interface NetworkHandler {
    fun onConnectionFailed(){
        hideIndicator()
    }
    fun onErrorOccurred(){
        hideIndicator()
    }
    fun onSuccess(){
        hideIndicator()
    }
    fun showIndicator(){}
    fun hideIndicator(){}
}