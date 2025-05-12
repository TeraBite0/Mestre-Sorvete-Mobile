package com.example.terabitemobile.data.api

import com.example.terabitemobile.data.classes.*
import com.example.terabitemobile.data.models.CardapioItem
import com.example.terabitemobile.data.models.CardapioPost
import retrofit2.Call

import retrofit2.http.*

interface LoginApiService {
    @POST("usuarios/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}