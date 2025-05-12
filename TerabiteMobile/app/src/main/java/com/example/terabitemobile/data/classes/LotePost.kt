package com.example.terabitemobile.data.classes

class LotePost (
    val nomeFornecedor: String,
    val dtEntrega: String?,
    val dtPedido: String?,
    val valorLote: Int,
    val loteProdutos: List<LoteProdutoResponse>,
)