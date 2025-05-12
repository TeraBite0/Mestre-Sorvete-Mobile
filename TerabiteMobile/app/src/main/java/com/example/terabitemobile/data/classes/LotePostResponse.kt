package com.example.terabitemobile.data.classes

import java.util.Date

class LotePostResponse (
    val id: Int,
    val fornecedor: String,
    val dtEntrega: Date,
    val dtVencimento: Date,
    val dtPedido: Date,
    val valorLote: Int,
    val status: String,
    val observacao: String,
    val loteProdutos: List<LoteProdutoResponse>
)