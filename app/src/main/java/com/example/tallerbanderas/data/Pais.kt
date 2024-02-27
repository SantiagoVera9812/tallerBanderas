package com.example.tallerbanderas.data

import android.net.Uri

data class Pais(
    val name: String,
    val nativeName: String,
    val alpha3Code: String,
    val currencyName: String,
    val currencySymbol: String,
    val flagUri: Uri,
    val numericTelephone: String
)
