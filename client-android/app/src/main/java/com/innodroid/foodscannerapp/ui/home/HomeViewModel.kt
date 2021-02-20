package com.innodroid.foodscannerapp.ui.home

import androidx.lifecycle.*
import com.innodroid.foodscannerapp.services.Resource
import com.innodroid.foodscannerapp.services.auth.AuthenticationService
import com.innodroid.foodscannerapp.services.scans.ScansService
import com.innodroid.foodscannerapp.model.Scan
import kotlinx.coroutines.Dispatchers

class HomeViewModel(authService: AuthenticationService, scanService: ScansService): ViewModel() {
    val scans: LiveData<Resource<List<Scan>>> = authService.user.switchMap { user ->
        liveData(Dispatchers.IO) {
            if (user == null) {
                emit(Resource.notAuthenticated<List<Scan>>())
            } else {
                emit(Resource.loading<List<Scan>>())

                emitSource(scanService.getScans().map {
                    Resource.success(it)
                })
            }
        }
    }
}
