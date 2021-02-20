package com.innodroid.foodscannerapp.services.scans

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scans")
data class ScanStored(
    @PrimaryKey
    @ColumnInfo(name = "upc")
    val upc: String,

    @ColumnInfo(name = "brand")
    val brand: String?,

    @ColumnInfo(name = "label")
    val label: String?,

    @ColumnInfo(name = "date")
    val date: Long
) {
    val title: String
        get() {
            return if (brand.isNullOrBlank() && label.isNullOrBlank()) {
                "?"
            } else if (brand.isNullOrBlank()) {
                label ?: "?"
            } else if (label.isNullOrBlank()) {
                brand
            } else {
                "${brand.trim()} ${label.trim()}"
            }
        }
}
