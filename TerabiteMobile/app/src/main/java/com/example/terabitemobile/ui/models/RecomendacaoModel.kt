package com.example.terabitemobile.ui.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class RecomendacaoItem(
    val id: Int,
    val nome: String,
    val marca: String
)

class RecomendacaoModel : ViewModel() {

    private val _recomendacoes = MutableLiveData<List<RecomendacaoItem>?>()
    val recomendacoes: MutableLiveData<List<RecomendacaoItem>?> = _recomendacoes

    init {
        if (_recomendacoes.value == null) {
            _recomendacoes.value = listOf(
                RecomendacaoItem(id = 1, nome = "Sorvete de Banana", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 2, nome = "Chokito", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 3, nome = "Lolo 🚬", marca = "Bob Marley")
            )
        }
    }

    fun addRecomendacao(nome: String, marca: String) {
        val currentList = _recomendacoes.value?.toMutableList() ?: mutableListOf()
        val newId = currentList.maxOfOrNull { it.id }?.plus(1) ?: 1
        currentList.add(RecomendacaoItem(newId, nome, marca))
        _recomendacoes.value = currentList
    }

    fun deleteRecomendacao(id: Int) {
        val currentList = _recomendacoes.value?.toMutableList()
        currentList?.removeAll { it.id == id }
        _recomendacoes.value = currentList
    }
}