package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.classes.FornecedorRequest
import retrofit2.Call
import retrofit2.http.*

interface FornecedorApiService {
    @GET("/fornecedores")
    fun getFornecedores(): Call<List<FornecedorRequest>>
}