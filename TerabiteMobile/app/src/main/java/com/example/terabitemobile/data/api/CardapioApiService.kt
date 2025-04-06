package com.example.terabitemobile.data.api

import com.example.terabitemobile.ui.models.cardapioItem
import retrofit2.Call
import retrofit2.http.*

interface CardapioApiService {
    @GET("produtos")
    fun getProdutos(): Call<List<cardapioItem>>

    @POST("produtos")
    fun adicionarProduto(@Body produto: cardapioItem): Call<cardapioItem>

    @PUT("produtos/{id}")
    fun atualizarProduto(@Path("id") id: Int, @Body produto: cardapioItem): Call<cardapioItem>
}