package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.classes.EstoqueItem
import com.example.terabitemobile.data.classes.LotePost
import com.example.terabitemobile.data.classes.LoteProdutosPostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EstoqueApiService {
    @GET("produtos")
    fun getEstoque(): Call<List<EstoqueItem>>

    @POST("lotes")
    fun postLote(@Body lote: LotePost): Call<LoteProdutosPostResponse>
}