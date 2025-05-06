package com.example.terabitemobile.koin


import com.example.terabitemobile.data.api.*
import com.example.terabitemobile.data.models.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
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

    factory<EstoqueApiService> {
        get<Retrofit>().create(EstoqueApiService::class.java)
    }

    viewModel<EstoqueModel> {
        EstoqueModel(get<EstoqueApiService>())
    }

    factory<SaidaEstoqueApiService> {
        get<Retrofit>().create(SaidaEstoqueApiService::class.java)
    }

    viewModel<BaixasModel> {
        BaixasModel(get<SaidaEstoqueApiService>())
    }

    factory<DestaqueApiService> {
        get<Retrofit>().create(DestaqueApiService::class.java)
    }

    viewModel<DestaqueModel> {
        DestaqueModel(get<DestaqueApiService>())
    }

    factory<MarcaApiService> {
        get<Retrofit>().create(MarcaApiService::class.java)
    }

    viewModel<MarcaModel> {
        MarcaModel(get<MarcaApiService>())
    }

    factory<RecomendacaoApiService> {
        get<Retrofit>().create(RecomendacaoApiService::class.java)
    }

    viewModel<RecomendacaoModel>{
        RecomendacaoModel(get<RecomendacaoApiService>())
    }

    factory<TiposApiService> {
        get<Retrofit>().create(TiposApiService::class.java)
    }

    viewModel<SubtipoModel>{
        SubtipoModel(get<TiposApiService>())
    }


}
