package com.innodroid.foodscannerapp.services

import com.innodroid.foodscannerapp.services.ingredients.IngredientApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    // Not currently implemented; provides REST API for ingredients instead of firebase function
    private const val BASE_URL = "https://your-api-service.com/api/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val ingredientApi = getRetrofit().create(IngredientApi::class.java)
}
