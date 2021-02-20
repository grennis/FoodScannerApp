package com.innodroid.foodscannerapp.services.ingredients

import com.innodroid.foodscannerapp.model.Food

interface IngredientService {
    suspend fun getIngredients(upc: String, store: Boolean): Food
}
