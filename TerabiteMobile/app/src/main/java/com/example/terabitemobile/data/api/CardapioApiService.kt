package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.classes.CardapioItem
import com.example.terabitemobile.data.classes.CardapioPost
import retrofit2.Call
import retrofit2.http.*

interface CardapioApiService {
    @GET("produtos")
    fun getProdutos(): Call<List<CardapioItem>>

    @POST("produtos")
    fun adicionarProduto(@Body produto: CardapioPost): Call<CardapioItem>

    @PUT("produtos/{id}")
    fun atualizarProduto(
        @Path("id") id: Int,
        @Body produto: CardapioPost
    ): Call<CardapioItem>
}