package com.albo.test.retrofit.modelretrofit.exception

import java.io.IOException

class ConnectionException : IOException(){
    override val message: String?
        get() = "Tu dispositivo no cuenta con conexión a internet, revisa tu wifi o datos moviles"
}