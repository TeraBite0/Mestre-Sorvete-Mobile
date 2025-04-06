package com.example.terabitemobile.ui.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class cardapioItem(
    val id: Int,
    val nome: String,
    val marca: String,
    val tipo: String,
    val subtipo: String,
    val preco: Double,
    val qtdCaixa: Int,
    val qtdPorCaixa: Int,
    val ativo: Boolean,
    val temLactose: Boolean,
    val temGluten: Boolean
)

class CardapioModel : ViewModel() {

    private val _produtos = MutableLiveData<List<cardapioItem>?>()
    val produtos: MutableLiveData<List<cardapioItem>?> = _produtos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> = _error

    init {
        carregarProdutos()
    }

    fun carregarProdutos() {
        _isLoading.value = true
        RetrofitClient.cardapioService.getProdutos().enqueue(object : Callback<List<cardapioItem>> {
            override fun onResponse(call: Call<List<cardapioItem>>, response: Response<List<cardapioItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _produtos.value = response.body()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<cardapioItem>>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"

                // Carrega dados locais como fallback em caso de falha
                carregarDadosLocais()
            }
        })
    }

    private fun carregarDadosLocais() {
        // Mantém os dados estáticos como fallback
        _produtos.value = listOf(
            cardapioItem(
                id = 1,
                nome = "Sorvete Napolitano",
                marca = "Kibon",
                tipo = "Sorvete",
                subtipo = "Cremoso",
                preco = 19.99,
                qtdCaixa = 10,
                qtdPorCaixa = 20,
                ativo = true,
                temLactose = true,
                temGluten = false
            ),
            // Outros itens...
        )
    }

    fun adicionarProduto(novoProduto: cardapioItem) {
        _isLoading.value = true
        RetrofitClient.cardapioService.adicionarProduto(novoProduto).enqueue(object : Callback<cardapioItem> {
            override fun onResponse(call: Call<cardapioItem>, response: Response<cardapioItem>) {
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

            override fun onFailure(call: Call<cardapioItem>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha ao adicionar: ${t.message}"
            }
        })
    }

    fun atualizarProduto(produtoAtualizado: cardapioItem) {
        _isLoading.value = true
        RetrofitClient.cardapioService.atualizarProduto(produtoAtualizado.id, produtoAtualizado)
            .enqueue(object : Callback<cardapioItem> {
                override fun onResponse(call: Call<cardapioItem>, response: Response<cardapioItem>) {
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

                override fun onFailure(call: Call<cardapioItem>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = "Falha ao atualizar: ${t.message}"
                }
            })
    }
}