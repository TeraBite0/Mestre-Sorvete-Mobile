package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.EstoqueApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class EstoqueItem (
    val id: Int,
    val nome: String,
    val marca: String,
    val qtdCaixasEstoque: Int,
    val qtdPorCaixas: Int
)

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
}