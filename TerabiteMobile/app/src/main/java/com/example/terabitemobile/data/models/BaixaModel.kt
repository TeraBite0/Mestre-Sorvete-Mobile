package com.example.terabitemobile.data.models

import BaixaResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.SaidaEstoqueApiService
import com.example.terabitemobile.data.classes.BaixaItem
import com.example.terabitemobile.data.classes.BaixaRequest
import com.example.terabitemobile.data.api.BaixaDeleteRequest
import com.example.terabitemobile.data.api.BaixaItemDeleteRequest
import com.example.terabitemobile.data.classes.BaixaItemRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    fun adicionarBaixa(baixaRequest: BaixaRequest) {
        _isLoading.value = true
        _error.value = ""

        baixaService.postBaixa(baixaRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    carregarBaixas()
                } else {
                    try {
                        val errorBody = response.errorBody()?.string()
                        _error.value = "Erro ${response.code()}: $errorBody"
                    } catch (e: Exception) {
                        _error.value = "Erro ${response.code()}: ${response.message()}"
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }

    fun editarBaixa(id: Int, produtoId: Int, qtdCaixasSaida: Int) {
        _isLoading.value = true
        _error.value = ""

        val baixaRequest = BaixaItemRequest(
            produtoId = produtoId,
            qtdCaixasSaida = qtdCaixasSaida
        )

        baixaService.putBaixa(id, baixaRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    carregarBaixas()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }

    fun deletarBaixa(dtSaida: String, baixaItem: BaixaItem) {
        _isLoading.value = true
        _error.value = ""

        val deleteRequest = BaixaDeleteRequest(
            dtSaida = dtSaida,
            saidaEstoques = listOf(
                BaixaItemDeleteRequest(
                    id = baixaItem.id,
                    produtoId = baixaItem.produto.id,
                    qtdCaixasSaida = baixaItem.qtdCaixasSaida
                )
            )
        )

        baixaService.deleteBaixa(deleteRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    carregarBaixas()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }
}