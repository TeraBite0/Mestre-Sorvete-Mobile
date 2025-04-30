package com.example.terabitemobile.koin


import com.example.terabitemobile.data.api.CardapioApiService
import com.example.terabitemobile.data.api.RetrofitClient
import com.example.terabitemobile.data.api.RetrofitClient.BASE_URL
import com.example.terabitemobile.data.api.RetrofitClient.getRetrofit
import com.example.terabitemobile.data.api.RetrofitClient.loggingInterceptor
import com.example.terabitemobile.data.api.TokenInterceptor
import com.example.terabitemobile.data.models.CardapioModel
import com.example.terabitemobile.data.models.UsuarioTokenModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val moduloGeral = module {

    // single -> devolve a MESMA instância para todos que pedirem
    single<UsuarioTokenModel> {
        UsuarioTokenModel()
    }

    single<Retrofit> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(TokenInterceptor(get<UsuarioTokenModel>().token)) // interceptor de token
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        retrofit
    }

    // factory -> devolve uma NOVA instância para cada que pedir
    factory<CardapioApiService> {
        get<Retrofit>().create(CardapioApiService::class.java)
    }

    viewModel<CardapioModel> {
        CardapioModel(get<CardapioApiService>())
    }


}
