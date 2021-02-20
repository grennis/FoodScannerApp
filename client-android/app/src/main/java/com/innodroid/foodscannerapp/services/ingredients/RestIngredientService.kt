package com.innodroid.foodscannerapp.services.ingredients

import com.innodroid.foodscannerapp.services.RetrofitBuilder

private val ingredientsApi = RetrofitBuilder.ingredientApi

class RestIngredientService: IngredientService {
    override suspend fun getIngredients(upc: String, store: Boolean) = ingredientsApi.getIngredients(upc)
}
