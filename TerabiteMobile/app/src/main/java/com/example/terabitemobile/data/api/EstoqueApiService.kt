package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.classes.LotePost
import com.example.terabitemobile.data.classes.LotePostResponse
import com.example.terabitemobile.data.classes.LoteProdutosPostResponse
import com.example.terabitemobile.data.models.*
import retrofit2.Call
import retrofit2.http.*

interface EstoqueApiService {
    @GET("produtos")
    fun getEstoque(): Call<List<EstoqueItem>>

    @POST("lotes")
    fun postLote(@Body lote: LotePost): Call<LoteProdutosPostResponse>
}