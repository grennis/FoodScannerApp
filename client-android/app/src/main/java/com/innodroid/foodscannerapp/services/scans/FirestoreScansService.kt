package com.innodroid.foodscannerapp.services.scans

import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.innodroid.foodscannerapp.model.Scan
import com.innodroid.foodscannerapp.services.auth.AuthenticationService

class FirestoreScansService(val auth: AuthenticationService): ScansService {
    private val firestore = FirebaseFirestore.getInstance()

    override fun getScans(): LiveData<List<Scan>> {
        return auth.user.switchMap { user ->
            val result = MutableLiveData<List<Scan>>()

            if (user == null) {
                result.value = listOf()
            } else {
                firestore.collection("users/${user.id}/scans")
                        .orderBy("date", Query.Direction.DESCENDING)
                        .addSnapshotListener { snap, err ->
                    val scans = snap?.documents?.map { doc ->
                        Scan(doc.getString("upc")!!, doc.getString("brand"), doc.getString("label"), doc.getDate("date")?.time ?: 0L)
                    }

                    result.value = scans
                }
            }

            return@switchMap result
        }
    }
}
