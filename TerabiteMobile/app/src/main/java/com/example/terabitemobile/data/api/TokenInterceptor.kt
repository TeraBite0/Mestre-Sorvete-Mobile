package com.example.terabitemobile.data.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/*
Classe que "intercepta" todas as requisições,
adicionando o cabeçalho de autorização com o token
em todas as requisições
 */
class TokenInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider()
        Log.i("api", "token para header: $token")

        val builder = chain.request().newBuilder()
        token?.let {
            builder.header("Authorization", "Bearer $it")
        }

        return chain.proceed(builder.build())
    }
}