package com.innodroid.foodscannerapp.services.scans

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.innodroid.foodscannerapp.model.Scan

class SqliteScansService(val context: Context): ScansService {
    private val dao = ScansDatabase.getDatabase(context).scansDao()

    override fun getScans(): LiveData<List<Scan>> {
        val data = dao.getScans()

        return Transformations.map(data) { list ->
            list.map { s ->
                Scan(s.upc, s.brand, s.label, s.date)
            }
        }
    }

    suspend fun save(product: Scan) {
        return dao.save(
            ScanStored(
            product.upc,
            product.brand,
            product.label,
            product.date)
        )
    }

    suspend fun delete(upcs: List<String>) = dao.delete(upcs)
}
