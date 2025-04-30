package com.example.terabitemobile.data.api

import retrofit2.Call
import retrofit2.http.*

interface SaidaEstoqueApiService {
    @GET("saidas-estoque")
    fun getBaixas(): Call<List<BaixaItem>>

    @POST("saidas-estoque")
    fun postBaixa(@Body baixa: NovaSaidaRequest): Call<BaixaItem>
}