package com.innodroid.foodscannerapp.koin

import com.innodroid.foodscannerapp.FoodScannerApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

fun FoodScannerApplication.initializeKoin() {
    val self = this

    startKoin {
        androidLogger()
        androidContext(self)
        modules(appModule)
    }
}
