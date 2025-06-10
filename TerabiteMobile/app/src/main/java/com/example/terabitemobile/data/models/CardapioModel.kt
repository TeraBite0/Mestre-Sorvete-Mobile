package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.CardapioApiService
import com.example.terabitemobile.data.classes.CardapioItem
import com.example.terabitemobile.data.classes.CardapioPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardapioModel(val cardapioService: CardapioApiService) : ViewModel() {

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
        cardapioService.getProdutos().enqueue(object : Callback<List<CardapioItem>> {
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
        cardapioService.adicionarProduto(novoProduto).enqueue(object : Callback<CardapioItem> {
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

    fun atualizarProduto(produtoAtualizado: CardapioPost) {
        _isLoading.value = true
        cardapioService.atualizarProduto(produtoAtualizado.id, produtoAtualizado)
            .enqueue(object : Callback<CardapioItem> {
                override fun onResponse(call: Call<CardapioItem>, response: Response<CardapioItem>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        carregarProdutos()
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