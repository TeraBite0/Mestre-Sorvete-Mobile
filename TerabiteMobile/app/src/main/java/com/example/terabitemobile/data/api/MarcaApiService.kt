package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.classes.MarcaDelete
import com.example.terabitemobile.data.classes.MarcaItem
import retrofit2.Call
import retrofit2.http.*

interface MarcaApiService {
    @GET("marcas")
    fun getMarcas(): Call<List<MarcaItem>>

    @POST("marcas")
    fun postMarcas(@Body marca: String): Call<MarcaItem>

    @DELETE("marcas")
    fun deleteMarcas(@Path("id") id: Int): Call<MarcaDelete>
}