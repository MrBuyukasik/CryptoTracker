package com.hasancanbuyukasik.cryptotracker.ui.favorite

import android.content.Context
import com.hasancanbuyukasik.cryptotracker.data.local.CoinDao
import com.hasancanbuyukasik.cryptotracker.data.remote.CoinApi
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.FirebaseHelper
import javax.inject.Inject

class FavoriteCoinsRepository @Inject constructor(
    private val firebaseHelper: FirebaseHelper,

    ) {
    suspend fun getFavoriteCoins() = firebaseHelper.favoriteCoinsCollection()

}