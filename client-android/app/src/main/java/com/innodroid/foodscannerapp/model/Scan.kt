package com.innodroid.foodscannerapp.model

data class Scan(
    val upc: String,
    val brand: String?,
    val label: String?,
    val date: Long
) {
    val title: String
        get() {
            return if (brand.isNullOrBlank() && label.isNullOrBlank()) {
                "?"
            } else if (brand.isNullOrBlank()) {
                label ?: "?"
            } else if (label.isNullOrBlank()) {
                brand
            } else {
                "${brand.trim()} ${label.trim()}"
            }
        }
}
