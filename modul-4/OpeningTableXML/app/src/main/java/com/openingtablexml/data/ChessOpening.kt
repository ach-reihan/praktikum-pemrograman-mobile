package com.openingtablexml.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable