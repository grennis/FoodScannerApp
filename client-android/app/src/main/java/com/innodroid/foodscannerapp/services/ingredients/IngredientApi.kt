package com.innodroid.foodscannerapp.services.ingredients

import com.innodroid.foodscannerapp.model.Food
import retrofit2.http.GET
import retrofit2.http.Query

interface IngredientApi {
    @GET("product")
    suspend fun getIngredients(@Query("upc") upc: String): Food
}
