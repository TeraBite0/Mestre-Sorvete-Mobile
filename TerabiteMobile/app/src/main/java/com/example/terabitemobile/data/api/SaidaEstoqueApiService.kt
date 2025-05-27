package com.example.terabitemobile.data.api

import BaixaResponse
import com.example.terabitemobile.data.classes.BaixaRequest
import retrofit2.Call
import retrofit2.http.*

interface SaidaEstoqueApiService {
    @GET("saidas-estoque")
    fun getBaixas(): Call<List<BaixaResponse>>

    @POST("saidas-estoque")
    fun postBaixa(@Body baixaRequest: BaixaRequest): Call<Void>
}