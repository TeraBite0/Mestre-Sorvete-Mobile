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
                RecomendacaoItem(id = 3, nome = "Sorvete de Chocolate", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 4, nome = "Sorvete de Manga", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 5, nome = "Sorvete de Leite Condensado", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 6, nome = "Sorvete de Groselha", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 7, nome = "Sorvete de Limão", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 8, nome = "Sorvete de Melancia", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 9, nome = "Paleta de Maracujá", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 10, nome = "Sorvete de Morango", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 11, nome = "Sorvete de Uva", marca = "Mestre Sorvete"),
                RecomendacaoItem(id = 12, nome = "Sorvete de Coco queimado", marca = "Mestre Sorvete")
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