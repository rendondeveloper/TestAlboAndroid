package com.albo.test.retrofit.build

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.albo.test.retrofit.interceptor.ConnectionInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApp <T> private constructor(
    private val host: String,
    private val context: Context,
    private val apiInterface : Class<T>,
    private val interceptors : List<Interceptor>,
    private val isDebug : Boolean) {

    private fun retrofitClient() : Retrofit.Builder {

        val levelType: HttpLoggingInterceptor.Level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor().apply {
            level = levelType
        }

        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(logging)
            addInterceptor(ConnectionInterceptor(context))
        }

        interceptors.forEach { item ->
            httpClient.addInterceptor(item);
        }

        return Retrofit.Builder()
            .baseUrl(host)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
    }

    fun instance() : T {
        return retrofitClient()
            .build()
            .create(apiInterface)
    }

    class Build<T>(){
        private lateinit var host : String
        private lateinit var context : Context
        private lateinit var apiInterface : Class<T>
        private var interceptors :  List<Interceptor> = listOf()
        private var isDebug : Boolean = false

        fun setHost(host : String) : Build<T> {
            this.host = host
            return this
        }

        fun setContext(context: Context): Build<T> {
            this.context = context
            return this
        }

        fun setEnvironment(environment: Boolean): Build<T> {
            this.isDebug = environment
            return this
        }

        fun setClass(apiInterface : Class<T>): Build<T> {
            this.apiInterface = apiInterface
            return this
        }

        fun setInterceptors(interceptors: List<Interceptor>): Build<T> {
            this.interceptors = interceptors
            return this
        }

        fun builder() : RetrofitApp<T> {
            return RetrofitApp(
                    host,
                    context,
                    apiInterface,
                    interceptors,
                    isDebug
            );
        }
    }
}