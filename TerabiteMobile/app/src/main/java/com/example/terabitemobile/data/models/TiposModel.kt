package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.TiposApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class TipoItem(
    val id: Int,
    val nome: String
)

data class SubtipoItem(
    val id: Int,
    val tipo: TipoItem,
    val nome: String
)

data class SubtipoPost(
    val nome: String,
    val nomeTipo: String
)

class SubtipoModel(val subtipoService: TiposApiService) : ViewModel() {

    private val _subtipos = MutableLiveData<List<SubtipoItem>?>()
    val subtipos: MutableLiveData<List<SubtipoItem>?> = _subtipos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> = _error

    init {
        carregarSubtipos()
    }

    fun carregarSubtipos() {
        _error.value = ""
        _isLoading.value = true
        subtipoService.getSubtipos().enqueue(object : Callback<List<SubtipoItem>> {
            override fun onResponse(call: Call<List<SubtipoItem>>, response: Response<List<SubtipoItem>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _subtipos.value = response.body()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<SubtipoItem>>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }

    fun getSubtiposByTipo(tipoNome: String): List<SubtipoItem> {
        return _subtipos.value?.filter { it.tipo.nome == tipoNome } ?: emptyList()
    }
}