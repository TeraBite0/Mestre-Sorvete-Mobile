package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.models.DestaqueItem
import retrofit2.Call
import retrofit2.http.*


interface DestaqueApiService {
    data class DestaqueUpdateRequest(
        val produtoId: Int,
        val texto: String
    )
    @GET("produtos/destaque")
    fun getDestaque(): Call<DestaqueItem>

    @PUT("produtos/destaque")
    fun putDestaque(@Body request: DestaqueUpdateRequest): Call<DestaqueItem>
}