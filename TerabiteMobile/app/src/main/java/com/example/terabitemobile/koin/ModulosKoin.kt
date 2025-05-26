package com.example.terabitemobile.koin

import com.example.terabitemobile.data.api.*
import com.example.terabitemobile.data.classes.FornecedorRequest
import com.example.terabitemobile.data.classes.LoginResponse
import com.example.terabitemobile.data.models.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.example.terabitemobile.data.models.LoginModel.LoginState

// Se estiver desenvolvendo localmente, use o endereço IP do
// seu computador (por exemplo, 192.168.1.100), a URL ficaria assim:

private const val BASE_URL = "http://44.192.125.22/api/"
// private const val BASE_URL = "http://192.168.1.100:8080"
//    private const val BASE_URL = "http://192.168.15.11:8080/"
//private const val BASE_URL = "http://10.18.8.41:8080/"
//private const val BASE_URL = "http://10.18.34.16:8080/"


val moduloGeral = module {
    // single -> devolve a MESMA instância para todos que pedirem
    single<LoginResponse> {
        LoginResponse()
    }

    single {
        val loginResponse: LoginResponse = get()
        TokenInterceptor { loginResponse.token }
    }

    single<Retrofit> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(get<TokenInterceptor>()) // Modificado aqui
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
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

    viewModel<BaixaModel> {
        BaixaModel(get<SaidaEstoqueApiService>())
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


    factory<LoginApiService> {
        get<Retrofit>().create(LoginApiService::class.java)
    }

    viewModel<LoginModel> {
        LoginModel(get<LoginApiService>(), get<LoginResponse>())
    }

    factory<FornecedorApiService> {
        get<Retrofit>().create(FornecedorApiService::class.java)
    }

    viewModel<FornecedorModel>  {
        FornecedorModel(get<FornecedorApiService>())
    }

}
