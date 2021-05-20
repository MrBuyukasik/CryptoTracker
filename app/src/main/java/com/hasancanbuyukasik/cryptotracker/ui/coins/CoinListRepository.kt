package com.hasancanbuyukasik.cryptotracker.ui.coins

import android.content.Context
import androidx.sqlite.db.SupportSQLiteQuery
import com.hasancanbuyukasik.cryptotracker.data.local.CoinDao
import com.hasancanbuyukasik.cryptotracker.data.remote.CoinApi
import com.hasancanbuyukasik.cryptotracker.data.remote.INetworkResponseHandling
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.DataHolder
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.RequestHelper
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel
import javax.inject.Inject

class CoinListRepository @Inject constructor(
    private val apiService: CoinApi,
    private val context: Context,
    private val coinDao: CoinDao
) {
    suspend fun getCoinList(
        iNetworkResponseHandling: INetworkResponseHandling
    ): DataHolder<List<CoinResponseModel>>? {
        return object : RequestHelper<List<CoinResponseModel>>() {
            override suspend fun createNetworkRequest(): List<CoinResponseModel> {
                return apiService.getCoinList()
            }

        }.loadRequest(iNetworkResponseHandling, true, context)
    }

    suspend fun searchCoinByNameOrSymbol(query: SupportSQLiteQuery) = coinDao.searchCoin(query)

    suspend fun insertCoinListLocalDatabase(coinList: List<CoinResponseModel>) =
        coinDao.insertCoinList(coinList)
}
