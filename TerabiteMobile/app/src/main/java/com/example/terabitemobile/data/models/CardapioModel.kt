package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.RetrofitClient
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class CardapioItem(
    val id: Int,
    val nome: String,

    @SerializedName("marca")
    val nomeMarca: String,

    val tipo: String,

    @SerializedName("subtipo")
    val nomeSubtipo: String,

    val preco: Double,
    val qtdCaixa: Int,
    val qtdPorCaixas: Int,
    val ativo: Boolean,
    val temLactose: Boolean,
    val temGluten: Boolean
)

data class CardapioPost (
    val nome: String,
    val nomeSubtipo: String,
    val nomeMarca: String,
    val preco: Double,
    val qtdPorCaixas: Int,
    val temLactose: Boolean,
    val temGluten: Boolean
)

class CardapioModel : ViewModel() {

    private val _produtos = MutableLiveData<List<CardapioItem>?>()
    val produtos: MutableLiveData<List<CardapioItem>?> = _produtos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> = _error

    init {
        carregarProdutos()
    }

    fun carregarProdutos() {
        _error.value = ""
        _isLoading.value = true
        RetrofitClient.cardapioService.getProdutos().enqueue(object : Callback<List<CardapioItem>> {
            override fun onResponse(call: Call<List<CardapioItem>>, response: Response<List<CardapioItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _produtos.value = response.body()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<CardapioItem>>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }

    fun adicionarProduto(novoProduto: CardapioPost) {
        _isLoading.value = true
        RetrofitClient.cardapioService.adicionarProduto(novoProduto).enqueue(object : Callback<CardapioItem> {
            override fun onResponse(call: Call<CardapioItem>, response: Response<CardapioItem>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val produtoAdicionado = response.body()
                    produtoAdicionado?.let {
                        val listaAtual = _produtos.value?.toMutableList() ?: mutableListOf()
                        listaAtual.add(it)
                        _produtos.value = listaAtual
                    }
                } else {
                    _error.value = "Erro ao adicionar: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<CardapioItem>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha ao adicionar: ${t.message}"
            }
        })
    }

    fun atualizarProduto(produtoAtualizado: CardapioItem) {
        _isLoading.value = true
        RetrofitClient.cardapioService.atualizarProduto(produtoAtualizado.id, produtoAtualizado)
            .enqueue(object : Callback<CardapioItem> {
                override fun onResponse(call: Call<CardapioItem>, response: Response<CardapioItem>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val listaAtualizada = _produtos.value?.map { item ->
                            if (item.id == produtoAtualizado.id) produtoAtualizado else item
                        }
                        _produtos.value = listaAtualizada
                    } else {
                        _error.value = "Erro ao atualizar: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<CardapioItem>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = "Falha ao atualizar: ${t.message}"
                }
            })
    }
}