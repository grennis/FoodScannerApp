package com.innodroid.foodscannerapp

import android.app.Application
import com.innodroid.foodscannerapp.koin.initializeKoin
import com.innodroid.foodscannerapp.services.auth.AuthenticationService
import org.koin.android.ext.android.inject

class FoodScannerApplication: Application() {
    private val auth by inject<AuthenticationService>()

    override fun onCreate() {
        super.onCreate()

        initializeKoin()

        auth.listenForChanges()
    }
}
