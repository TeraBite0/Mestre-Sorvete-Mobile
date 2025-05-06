package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.models.CardapioItem
import androidx.lifecycle.LiveData
import com.example.terabitemobile.data.api.RecomendacaoApiService
import com.example.terabitemobile.data.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

data class RecomendacaoItem(
    val id: Int,
    val produto: CardapioItem,
)

class RecomendacaoModel(val recomendacaoService: RecomendacaoApiService) : ViewModel() {

    private val _recomendacoes = MutableLiveData<List<RecomendacaoItem>?>()
    val recomendacoes: MutableLiveData<List<RecomendacaoItem>?> = _recomendacoes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> = _error

    init {
        carregarRecomendacoes()
    }

    fun carregarRecomendacoes() {
        _error.value = ""
        _isLoading.value = true

        recomendacaoService.getRecomendacoes().enqueue(object : Callback<List<RecomendacaoItem>> {
            override fun onResponse(
                call: Call<List<RecomendacaoItem>>, response: Response<List<RecomendacaoItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _recomendacoes.value = response.body()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<RecomendacaoItem>>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }

    fun updateRecomendacao(idRecomendacao: Int, produtoId: Int) {
        _error.value = ""
        _isLoading.value = true

        recomendacaoService.adicionarRecomendacao(idRecomendacao, produtoId).enqueue(object : Callback<RecomendacaoItem> {
            override fun onResponse(call: Call<RecomendacaoItem>, response: Response<RecomendacaoItem>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val updatedItem = response.body()

                    if (updatedItem != null) {
                        val currentList = _recomendacoes.value?.toMutableList() ?: mutableListOf()
                        val index = currentList.indexOfFirst { it.id == updatedItem.id }
                        if (index != -1) {
                            currentList[index] = updatedItem
                            _recomendacoes.value = currentList
                        }
                    } else {
                        carregarRecomendacoes()
                    }
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<RecomendacaoItem>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }
}