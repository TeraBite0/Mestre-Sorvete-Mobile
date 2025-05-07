package com.example.terabitemobile.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.SaidaEstoqueApiService
import com.example.terabitemobile.data.models.CardapioModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class SaidaEstoqueResponse(
    val dtSaida: String,
    val saidaEstoques: List<SaidaEstoque>
)

data class SaidaEstoque(
    val id: Int,
    val produto: CardapioItem,
    val qtdCaixasSaida: Int
)


class BaixasModel(val baixasService: SaidaEstoqueApiService) : ViewModel() {
//    private val _baixas = MutableLiveData<List<SaidaEstoqueResponse>>()
//    val baixas: LiveData<List<SaidaEstoqueResponse>> = _baixas
//
//    private val _produtos = MutableLiveData<List<Produto>>()
//    val produtos: LiveData<List<Produto>> = _produtos
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> = _error
//
//    init {
//        carregarBaixas()
//        carregarProdutos()
//    }
//
//    fun carregarBaixas() {
//        _error.value = ""
//        _isLoading.value = true
//        baixasService.getBaixas()
//            .enqueue(object : Callback<List<SaidaEstoqueResponse>> {
//                override fun onResponse(
//                    call: Call<List<SaidaEstoqueResponse>>,
//                    response: Response<List<SaidaEstoqueResponse>>
//                ) {
//                    _isLoading.value = false
//                    if (response.isSuccessful) {
//                        _baixas.value = response.body() ?: emptyList()
//                    } else {
//                        _error.value = "Erro ${response.code()}: ${response.message()}"
//                    }
//                }
//
//                override fun onFailure(call: Call<List<SaidaEstoqueResponse>>, t: Throwable) {
//                    _isLoading.value = false
//                    _error.value = "Falha na conexão: ${t.message}"
//                }
//            })
//    }
//
//    private fun carregarProdutos() {
//        // Implementar chamada para carregar produtos se necessário
//    }
//
//    fun addBaixa(data: String, produtoId: Int, qtdCaixas: Int) {
//        _isLoading.value = true
//
//        val novaSaida = NovaSaidaRequest(
//            dtSaida = data.toString(),
//            saidaEstoques = listOf(
//                NovaSaidaItem(
//                    produtoId = produtoId,
//                    qtdCaixasSaida = qtdCaixas
//                )
//            )
//        )
//
//        baixasService.postBaixa(novaSaida)
//            .enqueue(object : Callback<SaidaEstoqueResponse> {
//                override fun onResponse(
//                    call: Call<SaidaEstoqueResponse>,
//                    response: Response<SaidaEstoqueResponse>
//                ) {
//                    _isLoading.value = false
//                    if (response.isSuccessful) {
//                        carregarBaixas() // Recarrega a lista após adicionar
//                    } else {
//                        _error.value = "Erro ao adicionar: ${response.message()}"
//                    }
//                }
//
//                override fun onFailure(call: Call<SaidaEstoqueResponse>, t: Throwable) {
//                    _isLoading.value = false
//                    _error.value = "Falha ao adicionar: ${t.message}"
//                }
//            })
//    }
}