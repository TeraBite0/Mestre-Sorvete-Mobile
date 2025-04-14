package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.models.CardapioItem
import com.example.terabitemobile.data.models.RecomendacaoItem
import retrofit2.Call
import retrofit2.http.*

interface RecomendacaoApiService {
    @GET("produtos/recomendacao")
    fun getRecomendacoes(): Call<List<RecomendacaoItem>>

    @PUT("produtos/recomendacao/{id}")
    fun adicionarRecomendacao(
        @Path("id") idRecomendacao: Int,
        @Body produtoId: Int
    ): Call<RecomendacaoItem>
}