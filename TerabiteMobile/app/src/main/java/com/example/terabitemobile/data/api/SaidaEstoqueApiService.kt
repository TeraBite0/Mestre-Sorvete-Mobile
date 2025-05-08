package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.models.*
import retrofit2.Call
import retrofit2.http.*

interface SaidaEstoqueApiService {
    @GET("saidas-estoque")
    fun getBaixas(): Call<List<BaixaResponse>>
}