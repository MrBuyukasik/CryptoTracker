package com.hasancanbuyukasik.cryptotracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinList(coinList: List<CoinResponseModel>): List<Long>

    @RawQuery(observedEntities = [CoinResponseModel::class])
    suspend fun searchCoin(query: SupportSQLiteQuery): List<CoinResponseModel>
}