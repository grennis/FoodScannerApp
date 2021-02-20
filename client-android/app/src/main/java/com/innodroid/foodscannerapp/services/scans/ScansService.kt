package com.innodroid.foodscannerapp.services.scans

import androidx.lifecycle.LiveData
import com.innodroid.foodscannerapp.model.Scan

interface ScansService {
    fun getScans(): LiveData<List<Scan>>
}
