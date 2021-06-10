package com.albo.test.retrofit.interceptor

import android.content.Context
import android.net.ConnectivityManager
import com.albo.test.retrofit.modelretrofit.exception.ConnectionException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor constructor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if(!isConnected()){
            throw ConnectionException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isConnected() : Boolean{
        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}