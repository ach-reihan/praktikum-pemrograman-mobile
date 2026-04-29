package com.openingtablecompose.data

data class ChessOpening(
    val id: String,
    val name: String,
    val ecoCode: String,
    val moves: String,
    val category: String,
    val description: String,
    val imageUrl: String,
    val externalUrl: String,
    val isFeatured: Boolean = false
)