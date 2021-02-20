package com.innodroid.foodscannerapp.model

data class Food(
    val brand: String?,
    val label: String?,
    val image: String?,
    val ingredients: List<Ingredient>?
)
