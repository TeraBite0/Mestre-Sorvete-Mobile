package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.models.SubtipoItem
import com.example.terabitemobile.data.models.SubtipoPost
import com.example.terabitemobile.data.models.*
import retrofit2.Call
import retrofit2.http.*

interface TiposApiService {
    @GET("subtipos")
    fun getSubtipos(): Call<List<SubtipoItem>>

    @POST("subtipos")
    fun adicionarSubtipo(
        @Body subtipo: SubtipoPost
    ): Call<SubtipoItem>
}