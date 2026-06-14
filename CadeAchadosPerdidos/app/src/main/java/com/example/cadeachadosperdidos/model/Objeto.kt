package com.example.cadeachadosperdidos.model

data class Objeto(
    val id: Int? = null,
    val nome: String,
    val categoria: String,
    val status: String,
    val local: String? = null,
    val descricao: String? = null,
    val recomendacao: String? = null
)