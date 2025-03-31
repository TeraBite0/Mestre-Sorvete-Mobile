package com.example.terabitemobile.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terabitemobile.ui.screens.Produto

data class cardapioItem(
    val id: Int,
    val nome: String,
    val marca: String,
    val tipo: String,
    val subtipo: String,
    val preco: String,
    val qtdCaixa: Int,
    val qtdPorCaixa: Int,
    val ativo: Boolean,
    val temLactose: Boolean,
    val temGluten: Boolean
)

class CardapioModel : ViewModel() {

    private val _produtos = MutableLiveData<List<cardapioItem>>()

    // Lista pública imutável (UI observa esta)
    val produtos: LiveData<List<cardapioItem>> get() = _produtos

    init {
        _produtos.value = listOf(
            cardapioItem(
                id = 1,
                nome = "Sorvete Napolitano",
                marca = "Kibon",
                tipo = "Sorvete",
                subtipo = "Cremoso",
                preco = "19,99",
                qtdCaixa = 10,
                qtdPorCaixa = 20,
                ativo = true,
                temLactose = true,
                temGluten = false
            ), cardapioItem(
                id = 2,
                nome = "Picolé de Limão",
                marca = "Frutto",
                tipo = "Picolé",
                subtipo = "Frutas",
                preco = "3,50",
                qtdCaixa = 24,
                qtdPorCaixa = 48,
                ativo = false,
                temLactose = true,
                temGluten = false
            ), cardapioItem(
                id = 3,
                nome = "Açaí Tradicional",
                marca = "Açaí do Pará",
                tipo = "Açaí",
                subtipo = "Natural",
                preco = "15,90",
                qtdCaixa = 12,
                qtdPorCaixa = 24,
                ativo = true,
                temLactose = true,
                temGluten = false
            ), cardapioItem(
                id = 4,
                nome = "Torta Alemã",
                marca = "Nestlé",
                tipo = "Sorvete",
                subtipo = "Torta",
                preco = "24,99",
                qtdCaixa = 8,
                qtdPorCaixa = 16,
                ativo = true,
                temLactose = true,
                temGluten = false
            ), cardapioItem(
                id = 5,
                nome = "Milkshake Chocolate",
                marca = "Bauducco",
                tipo = "Bebida",
                subtipo = "Milkshake",
                preco = "12,75",
                qtdCaixa = 15,
                qtdPorCaixa = 30,
                ativo = false,
                temLactose = true,
                temGluten = false
            ), cardapioItem(
                id = 6,
                nome = "Sundae Caramelo",
                marca = "McDonald's",
                tipo = "Sobremesa",
                subtipo = "Sundae",
                preco = "9,90",
                qtdCaixa = 20,
                qtdPorCaixa = 40,
                ativo = true,
                temLactose = true,
                temGluten = false
            )
        )
    }

    fun adicionarProduto(novoProduto: cardapioItem) {
        val listaAtual = _produtos.value?.toMutableList() ?: mutableListOf()
        listaAtual.add(novoProduto)
        _produtos.value = listaAtual
    }

    fun atualizarProduto(produtoAtualizado: cardapioItem) {
        _produtos.value = _produtos.value?.map { item ->
            if (item.id == produtoAtualizado.id) produtoAtualizado else item
        }
    }

    fun alternarStatusProduto(id: Int) {
        _produtos.value = _produtos.value?.map { item ->
            if (item.id == id) item.copy(ativo = !item.ativo) else item
        }
    }
}