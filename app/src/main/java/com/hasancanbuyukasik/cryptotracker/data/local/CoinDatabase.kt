package com.hasancanbuyukasik.cryptotracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel


@Database(entities = [CoinResponseModel::class], version = 1, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao

    companion object {
        @Volatile
        private var instance: CoinDatabase? = null

        fun getDatabase(context: Context): CoinDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, CoinDatabase::class.java, "CoinDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }
}