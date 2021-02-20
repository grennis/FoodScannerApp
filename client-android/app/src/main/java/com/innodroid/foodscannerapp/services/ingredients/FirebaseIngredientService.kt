package com.innodroid.foodscannerapp.services.ingredients

import com.google.firebase.functions.FirebaseFunctions
import com.innodroid.foodscannerapp.model.Ingredient
import com.innodroid.foodscannerapp.model.Food
import com.innodroid.foodscannerapp.model.SensitivityLevel
import kotlinx.coroutines.tasks.await

class FirebaseIngredientService: IngredientService {
    override suspend fun getIngredients(upc: String, store: Boolean): Food {
        val parameters = hashMapOf("upc" to upc, "store" to store)

        val result = FirebaseFunctions.getInstance()
                .getHttpsCallable("processScan")
                .call(parameters)
                .await()
                .data as HashMap<*, *>

        return Food(
                result["brand"] as String?,
                result["label"] as String?,
                result["imageURL"] as String?,
                parseIngredients(result["ingredients"] as? List<HashMap<*, *>>)
        )
    }

    private fun parseIngredients(list: List<java.util.HashMap<*, *>>?): List<Ingredient>? {
        list ?: return listOf()

        return list.map {
            Ingredient(it["text"] as String, SensitivityLevel.values()[it["level"] as Int])
        }
    }
}
