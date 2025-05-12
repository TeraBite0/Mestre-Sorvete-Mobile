package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.models.MarcaItem
import retrofit2.Call
import retrofit2.http.*

interface MarcaApiService {
    @GET("marcas")
    fun getMarcas(): Call<List<MarcaItem>>

    @POST("marcas")
    fun postMarcas(@Body marca: String): Call<MarcaItem>
}