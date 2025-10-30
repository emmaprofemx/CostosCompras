package com.example.costoscompras

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int = 0
)
