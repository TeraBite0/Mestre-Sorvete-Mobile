package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class DestaqueItem(
    val id: Int,
    val nome: String,
    val descricao: String
)

class DestaqueModel : ViewModel() {

    private val _destaque = MutableLiveData<List<DestaqueItem>?>()
    val destaque: MutableLiveData<List<DestaqueItem>?> = _destaque

    init {
            _destaque.value = listOf(
                DestaqueItem(id = 1, nome = "Paleta de Maracujá", descricao = "Teste")
            )
    }

    fun editDestaque(id: Int, novoNome: String, novaDescricao: String) {
        val currentList = _destaque.value?.toMutableList()
        val index = currentList?.indexOfFirst { it.id == id }
        if (index != null && index != -1) {
            currentList[index] = currentList[index].copy(nome = novoNome, descricao = novaDescricao)
            _destaque.value = currentList
        }
    }
}