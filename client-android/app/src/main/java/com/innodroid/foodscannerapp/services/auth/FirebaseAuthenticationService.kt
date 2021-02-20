package com.innodroid.foodscannerapp.services.auth

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.innodroid.foodscannerapp.model.User

class FirebaseAuthenticationService: AuthenticationService {
    private val auth = FirebaseAuth.getInstance();
    private val _user = MutableLiveData<User>()

    override val user = _user

    override fun listenForChanges() {
        auth.addAuthStateListener {
            updateUser()
        }

        updateUser()
    }

    private fun updateUser() {
        val user = auth.currentUser

        if (user == null) {
            _user.value = null
            return
        }

        _user.value = User(user.uid, user.displayName ?: "", user.photoUrl)
    }
}
