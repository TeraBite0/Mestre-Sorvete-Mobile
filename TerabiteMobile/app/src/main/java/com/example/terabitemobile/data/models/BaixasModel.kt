package com.example.terabitemobile.data.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

data class BaixaItem(
    val id: Int,
    val nome: String,
    val qtdLotes: Int,
    val data: LocalDate
)

class BaixasModel : ViewModel() {

    private val _baixas = MutableLiveData<List<BaixaItem>?>()
    val baixas: MutableLiveData<List<BaixaItem>?> = _baixas

    init {
        if (_baixas.value == null) {
            _baixas.value = listOf(
                BaixaItem(id = 1, nome = "Picolés de Groselha", 5, LocalDate.parse("2025-04-01")),
                BaixaItem(id = 2, nome = "Sorvete de Chocolate", 15, LocalDate.parse("2025-03-10")),
                BaixaItem(id = 3, nome = "Picolé de Abacaxi", 3, LocalDate.parse("2024-12-10")),
                BaixaItem(id = 4, nome = "Picolé de Tutti-Fruti", 3, LocalDate.parse("2024-12-23"))
            )
        }
    }

    fun addBaixa(nome: String, qtdLotes: Int, data: LocalDate) {
        val currentList = _baixas.value?.toMutableList() ?: mutableListOf()
        val newId = currentList.maxOfOrNull { it.id }?.plus(1) ?: 1
        currentList.add(BaixaItem(newId, nome, qtdLotes, data))
        _baixas.value = currentList
    }


//    fun deleteBaixa(id: Int) {
//        val currentList = _baixas.value?.toMutableList()
//        currentList?.removeAll { it.id == id }
//        _baixas.value = currentList
//    }
}