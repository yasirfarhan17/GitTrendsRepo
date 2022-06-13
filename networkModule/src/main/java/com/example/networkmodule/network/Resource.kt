package com.example.networkmodule.network

sealed class Resources<T>(val data:T?=null,val message:String?=null,val httpCode:String?=null) {
    class Success<T>(data:T,httpCode:String):Resources<T>(data,httpCode)
    class Error<T>(message: String,httpCode:String):Resources<T>(null,message,httpCode)
    class Loading<T>():Resources<T>()
}