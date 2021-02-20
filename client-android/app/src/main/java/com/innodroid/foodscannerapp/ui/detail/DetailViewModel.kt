package com.innodroid.foodscannerapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.innodroid.foodscannerapp.services.ingredients.IngredientService
import com.innodroid.foodscannerapp.services.Resource
import kotlinx.coroutines.Dispatchers

class DetailViewModel(private val ingredientService: IngredientService) : ViewModel() {
    fun getIngredients(upc: String, store: Boolean) = liveData(Dispatchers.IO) {
        emit(Resource.loading())

        try {
            val searchUpc = if (upc.length == 12) "0$upc" else upc
            emit(Resource.success(data = ingredientService.getIngredients(searchUpc, store)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
