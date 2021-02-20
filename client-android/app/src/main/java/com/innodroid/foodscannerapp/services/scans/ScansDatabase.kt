package com.innodroid.foodscannerapp.services.scans

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ScanStored::class), version = 1, exportSchema = false)
abstract class ScansDatabase: RoomDatabase() {
    abstract fun scansDao(): ScansDao

    companion object {
        @Volatile
        private var INSTANCE: ScansDatabase? = null

        fun getDatabase(context: Context): ScansDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScansDatabase::class.java,
                    "food_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
