package com.example.terabitemobile.data.api

import BaixaResponse
import com.example.terabitemobile.data.classes.BaixaItemRequest
import com.example.terabitemobile.data.classes.BaixaRequest
import retrofit2.Call
import retrofit2.http.*

interface SaidaEstoqueApiService {
    @GET("saidas-estoque")
    fun getBaixas(): Call<List<BaixaResponse>>

    @POST("saidas-estoque")
    fun postBaixa(@Body baixaRequest: BaixaRequest): Call<Void>

    @PUT("saidas-estoque/{id}")
    fun putBaixa(@Path("id") id: Int, @Body baixaRequest: BaixaItemRequest): Call<Void>

    @HTTP(method = "DELETE", path = "saidas-estoque", hasBody = true)
    fun deleteBaixa(@Body deleteRequest: BaixaDeleteRequest): Call<Void>
}

data class BaixaDeleteRequest(
    val dtSaida: String,
    val saidaEstoques: List<BaixaItemDeleteRequest>
)

data class BaixaItemDeleteRequest(
    val id: Int,
    val produtoId: Int,
    val qtdCaixasSaida: Int
)