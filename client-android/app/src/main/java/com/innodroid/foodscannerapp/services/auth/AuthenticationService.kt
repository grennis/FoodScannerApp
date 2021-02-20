package com.innodroid.foodscannerapp.services.auth

import androidx.lifecycle.LiveData
import com.innodroid.foodscannerapp.model.User

interface AuthenticationService {
    val user: LiveData<User?>

    fun listenForChanges()
}
