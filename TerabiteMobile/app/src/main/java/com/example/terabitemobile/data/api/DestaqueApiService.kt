package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.models.DestaqueItem
import retrofit2.Call
import retrofit2.http.*

data class DestaqueUpdateRequest(
    val produtoId: Int,
    val texto: String
)

interface DestaqueApiService {
    @GET("produtos/destaque")
    fun getDestaque(): Call<DestaqueItem>

    @PUT("produtos/destaque")
    fun putDestaque(@Body body: DestaqueUpdateRequest): Call<DestaqueItem>
}