package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.MarcaApiService
import com.example.terabitemobile.data.classes.MarcaItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarcaModel(val marcaService: MarcaApiService) : ViewModel() {

    private val _marcas = MutableLiveData<List<MarcaItem>>()
    val marcas: MutableLiveData<List<MarcaItem>> = _marcas

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> = _error

    init {
        carregarMarcas()
    }

    fun carregarMarcas() {
        _error.value = ""
        _isLoading.value = true
        marcaService.getMarcas().enqueue(object : Callback<List<MarcaItem>> {
            override fun onResponse(
                call: Call<List<MarcaItem>>,
                response: Response<List<MarcaItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _marcas.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<MarcaItem>>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }

    fun addMarca(nome: String) {
        _isLoading.value = true
        marcaService.postMarcas(nome).enqueue(object : Callback<MarcaItem> {
            override fun onResponse(call: Call<MarcaItem>, response: Response<MarcaItem>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { novaMarca ->
                        val currentList = _marcas.value?.toMutableList() ?: mutableListOf()
                        currentList.add(novaMarca)
                        _marcas.value = currentList
                    }
                } else {
                    _error.value = "Erro ao adicionar: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<MarcaItem>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha ao adicionar: ${t.message}"
            }
        })
    }
}