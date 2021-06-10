package com.albo.test.retrofit.managercall

import android.util.Log
import com.albo.test.retrofit.modelretrofit.dataclass.ResponseData
import com.albo.test.retrofit.modelretrofit.dataclass.ResultApi
import com.albo.test.retrofit.modelretrofit.exception.BackendException
import com.albo.test.retrofit.modelretrofit.exception.ConnectionException
import retrofit2.HttpException
import retrofit2.Response

open class ManagerCall {
    private val MESSAGE_ERROR_GENERAL : String  = "Por el momento no es posible realizar esta operación, intentelo más tarde."
    private val MESSAGE_ERROR_GENERAL_NETWORK : String  = "Ocurrió un error al intentar conectar con nuestros servidores."
    private val TAG : String = "ManagerCall"

    suspend fun <T : Any> managerCallApi(call: suspend () -> Response<T>) : ResponseData<T?> {
        val result : ResultApi<T> = safeApiResult(call)
        val dataResponse : ResponseData<T?> = ResponseData()
        when(result){
            is ResultApi.Success -> {
                dataResponse.apply {
                    sucess = true
                    data = result.data
                }
            }
            is ResultApi.Error -> {
                dataResponse.exception = result.exception
            }
        }

        return dataResponse
    }

    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): ResultApi<T> {
        var exception : Exception? = null
        var data : T? = null
        try {

            val response = call.invoke()

            if (response.isSuccessful) {
                data = response.body()
            }

        }catch (ex : BackendException) {
            Log.d(TAG, "$TAG -> ${ex.message}")
            exception = Exception(ex.message)
        }catch (ex : ConnectionException){
            Log.d(TAG, "$TAG -> ${ex.message}")
            exception = ConnectionException()
        }catch (ex: HttpException){
            Log.d(TAG, "$TAG -> ${ex.message}")
            exception = Exception(MESSAGE_ERROR_GENERAL_NETWORK)
        }catch (ex: Exception){
            Log.d(TAG, "$TAG ->  ${ex.message}")
            exception = Exception(MESSAGE_ERROR_GENERAL)
        }
        finally {
            return if(exception != null){
                ResultApi.Error(exception)
            }else{
                ResultApi.Success(data!!)
            }
        }
    }
}