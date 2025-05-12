package com.example.terabitemobile.data.classes

class LoteProdutosPostResponse (
    val id: Int,
    val nome: String,
    val preco: Int,
    val qtdCaixasEstoque: Int,
    val qtdPorCaixas: Int,
    val isAtivo: Boolean,
    val disponivel: Boolean,
    val temLactose: Boolean,
    val subtipo: String,
    val tipo: String,
    val marca: String,
    val imagemUrl: String
)