package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.DestaqueApiService
import com.example.terabitemobile.data.api.DestaqueApiService.DestaqueUpdateRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class DestaqueItem(
    val id: Int,
    val produto: CardapioItem,
    val texto: String
)

class DestaqueModel(val destaqueService: DestaqueApiService) : ViewModel() {

    private val _destaque = MutableLiveData<DestaqueItem?>()
    val destaque: MutableLiveData<DestaqueItem?> = _destaque

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> = _error

    init {
            carregarDestaque()
    }

    fun carregarDestaque() {
        _error.value = ""
        _isLoading.value = true

        destaqueService.getDestaque().enqueue(object :
            Callback<DestaqueItem> {
            override fun onResponse(
                call: Call<DestaqueItem>, response: Response<DestaqueItem>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _destaque.value = response.body()
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<DestaqueItem>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }

    fun updateDestaque(produtoId: Int, texto: String) {
        _error.value = ""
        _isLoading.value = true

        val request = DestaqueUpdateRequest(produtoId = produtoId, texto = texto)

        destaqueService.putDestaque(request).enqueue(object : Callback<DestaqueItem> {
            override fun onResponse(call: Call<DestaqueItem>, response: Response<DestaqueItem>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val updatedItem = response.body()
                    if (updatedItem != null) {
                        _destaque.value = updatedItem
                    } else {
                        carregarDestaque()
                    }
                } else {
                    _error.value = "Erro ${response.code()}: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<DestaqueItem>, t: Throwable) {
                _isLoading.value = false
                _error.value = "Falha na conexão: ${t.message}"
            }
        })
    }
}