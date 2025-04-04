package com.example.terabitemobile.ui.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class MarcaItem(
    val id: Int,
    val nome: String
)

class MarcaModel : ViewModel() {

    private val _marcas = MutableLiveData<List<MarcaItem>?>()
    val marcas: MutableLiveData<List<MarcaItem>?> = _marcas

    init {
        if (_marcas.value == null) {
            _marcas.value = listOf(
                MarcaItem(id = 1, nome = "Legal"),
                MarcaItem(id = 2, nome = "Maneiro"),
                MarcaItem(id = 3, nome = "Teste"),
                MarcaItem(id = 4, nome = "Uou"),
                MarcaItem(id = 5, nome = "Mockito"),
                MarcaItem(id = 6, nome = "🦜")
            )
        }
    }

    fun addMarca(nome: String) {
        val currentList = _marcas.value?.toMutableList() ?: mutableListOf()
        val newId = currentList.maxOfOrNull { it.id }?.plus(1) ?: 1
        currentList.add(MarcaItem(newId, nome))
        _marcas.value = currentList
    }

    fun deleteMarca(id: Int) {
        val currentList = _marcas.value?.toMutableList()
        currentList?.removeAll { it.id == id }
        _marcas.value = currentList
    }
}