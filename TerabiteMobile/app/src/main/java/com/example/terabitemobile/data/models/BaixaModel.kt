package com.example.terabitemobile.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.SaidaEstoqueApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class BaixaResponse(
    val dtSaida: String,
    val saidaEstoques: List<BaixaItem>
)

data class BaixaItem(
    val id: Int,
    val qtdCaixasSaida: Int,
    val produto: CardapioItem,
    val dtSaida: String
)


class BaixaModel(private val baixaService: SaidaEstoqueApiService) : ViewModel() {

    private val _baixas = MutableLiveData<List<BaixaItem>?>()
    val baixas: LiveData<List<BaixaItem>?> = _baixas

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        carregarBaixas()
    }

    fun carregarBaixas() {
        _error.value = ""
        _isLoading.value = true

        baixaService.getBaixas().enqueue(object : Callback<List<BaixaResponse>> {
            override fun onResponse(
                call: Call<List<BaixaResponse>>,
                response: Response<List<BaixaResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val resposta = response.body()
                    val todasAsBaixas = resposta?.flatMap { baixaResponse ->
                        baixaResponse.saidaEstoques.map {
                            it.copy(dtSaida = baixaResponse.dtSaida)
                        }
                    } ?: emptyList()
                    _baixas.value = todasAsBaixas
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<BaixaResponse>>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }
}