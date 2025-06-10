package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.EstoqueApiService
import com.example.terabitemobile.data.classes.EstoqueItem
import com.example.terabitemobile.data.classes.LotePost
import com.example.terabitemobile.data.classes.LoteProdutosPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EstoqueModel(val estoqueService: EstoqueApiService) : ViewModel() {
    private val _estoque = MutableLiveData<List<EstoqueItem>?>()
    val estoque: MutableLiveData<List<EstoqueItem>?> = _estoque

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> = _error

    init {
        carregarEstoque()
    }

    fun carregarEstoque() {
        _error.value = ""
        _isLoading.value = true
        estoqueService.getEstoque().enqueue(object : Callback<List<EstoqueItem>> {
            override fun onResponse(call: Call<List<EstoqueItem>>, response: Response<List<EstoqueItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _estoque.value = response.body()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<EstoqueItem>>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }

    fun adicionarLote(novoLote: LotePost) {
        _error.value = ""
        _isLoading.value = true
        estoqueService.postLote(novoLote).enqueue(object : Callback<LoteProdutosPostResponse> {
            override fun onResponse(call: Call<LoteProdutosPostResponse>, response: Response<LoteProdutosPostResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    carregarEstoque()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<LoteProdutosPostResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }
}