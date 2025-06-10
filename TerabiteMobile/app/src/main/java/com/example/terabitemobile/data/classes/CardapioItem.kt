package com.example.terabitemobile.data.classes
import com.google.gson.annotations.SerializedName

data class CardapioItem(
    val id: Int,
    val nome: String,

    @SerializedName("marca")
    val nomeMarca: String,

    val tipo: String,

    @SerializedName("subtipo")
    val nomeSubtipo: String,

    val preco: Double,
    val qtdCaixa: Int,
    val qtdPorCaixas: Int,
    val ativo: Boolean,
    val temLactose: Boolean,
    val temGluten: Boolean,
    val imagemUrl: String?
)