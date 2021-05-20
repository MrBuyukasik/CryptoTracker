package com.hasancanbuyukasik.cryptotracker.ui.detail

import android.content.Context
import com.hasancanbuyukasik.cryptotracker.data.local.CoinDao
import com.hasancanbuyukasik.cryptotracker.data.remote.CoinApi
import com.hasancanbuyukasik.cryptotracker.data.remote.INetworkResponseHandling
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.DataHolder
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.FirebaseHelper
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.RequestHelper
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinDetailResponseModel
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel
import javax.inject.Inject


class CoinDetailRepository @Inject constructor(
    private val apiService: CoinApi,
    private val context: Context,
    private val coinDao: CoinDao,
    private val firebaseHelper: FirebaseHelper
) {

    suspend fun getCoinById(
        coinId: String,
        iNetworkResponseHandling: INetworkResponseHandling
    ): DataHolder<CoinDetailResponseModel>? {
        return object : RequestHelper<CoinDetailResponseModel>() {
            override suspend fun createNetworkRequest(): CoinDetailResponseModel {
                return apiService.getCoinDetail(coinId)
            }

        }.loadRequest(iNetworkResponseHandling, true, context)
    }

    suspend fun saveFavoriteCoin() = firebaseHelper.favoriteCoinsCollection()

    suspend fun deleteFavoriteCoin() = firebaseHelper.favoriteCoinsCollection()
}