package com.example.terabitemobile.data.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.data.api.FornecedorApiService
import com.example.terabitemobile.data.classes.FornecedorRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FornecedorModel(val fornecedorModel: FornecedorApiService) : ViewModel() {
    private val _fornecedores = MutableLiveData<List<FornecedorRequest>>()
    val fornecedores: MutableLiveData<List<FornecedorRequest>> = _fornecedores

    init {
        carregarFornecedores()
    }

    private fun carregarFornecedores() {
        fornecedorModel.getFornecedores().enqueue(object : Callback<List<FornecedorRequest>> {
            override fun onResponse(
                call: Call<List<FornecedorRequest>>, response: Response<List<FornecedorRequest>>
            ) {
                if (response.isSuccessful) {
                    _fornecedores.value = response.body()
                } else {
                    Log.e(
                        "com.example.terabitemobile.data.classes.FornecedorRequest",
                        "Erro ao carregar fornecedores: ${response.code()}"
                    )
                }
            }

            override fun onFailure(call: Call<List<FornecedorRequest>>, t: Throwable) {
                Log.e("FornecedorModel", "Erro ao carregar fornecedores", t)
            }
        })

    }
}