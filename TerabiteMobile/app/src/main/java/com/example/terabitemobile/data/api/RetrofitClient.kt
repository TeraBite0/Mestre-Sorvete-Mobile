package com.example.terabitemobile.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Se estiver desenvolvendo localmente, use o endereço IP do
    // seu computador (por exemplo, 192.168.1.100), a URL ficaria assim:
    // private const val BASE_URL = "http://192.168.1.100:8080"

    private const val BASE_URL = "http://192.168.11.47:8080/"
    //    private const val BASE_URL = "http://10.18.8.41:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val cardapioService: CardapioApiService = retrofit.create(CardapioApiService::class.java)
    val subtipoService: TiposApiService = retrofit.create(TiposApiService::class.java)
    val marcaService: MarcaApiService = retrofit.create(MarcaApiService::class.java)
    val recomendacaoService: RecomendacaoApiService = retrofit.create(RecomendacaoApiService::class.java)
    val estoqueService: EstoqueApiService = retrofit.create(EstoqueApiService::class.java)
    val destaqueService: DestaqueApiService = retrofit.create(DestaqueApiService::class.java)
}