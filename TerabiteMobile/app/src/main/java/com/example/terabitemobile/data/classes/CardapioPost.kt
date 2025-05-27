package com.example.terabitemobile.data.classes

data class CardapioPost (
    val id: Int,
    val nome: String,
    val nomeSubtipo: String,
    val nomeMarca: String,
    val preco: Double,
    val qtdPorCaixas: Int,
    val temLactose: Boolean,
    val temGluten: Boolean
)