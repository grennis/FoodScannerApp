package com.innodroid.foodscannerapp.koin

import com.innodroid.foodscannerapp.services.auth.AuthenticationService
import com.innodroid.foodscannerapp.services.auth.FirebaseAuthenticationService
import com.innodroid.foodscannerapp.services.ingredients.FirebaseIngredientService
import com.innodroid.foodscannerapp.services.ingredients.IngredientService
import com.innodroid.foodscannerapp.services.ingredients.RestIngredientService
import com.innodroid.foodscannerapp.services.scans.FirestoreScansService
import com.innodroid.foodscannerapp.services.scans.SqliteScansService
import com.innodroid.foodscannerapp.services.scans.ScansService
import com.innodroid.foodscannerapp.ui.detail.DetailViewModel
import com.innodroid.foodscannerapp.ui.home.HomeViewModel
import com.innodroid.foodscannerapp.ui.ingredients.IngredientsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IngredientService> { FirebaseIngredientService() }
    single<ScansService> { FirestoreScansService(get()) }
    single<AuthenticationService> { FirebaseAuthenticationService() }

    viewModel { DetailViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { IngredientsViewModel() }
}
