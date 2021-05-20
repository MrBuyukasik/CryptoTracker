package com.hasancanbuyukasik.cryptotracker.ui.splash

import android.content.Context
import com.hasancanbuyukasik.cryptotracker.data.remote.CoinApi
import com.hasancanbuyukasik.cryptotracker.data.remote.INetworkResponseHandling
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.DataHolder
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.FirebaseHelper
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.RequestHelper
import com.hasancanbuyukasik.cryptotracker.models.api.ApiStatusResponseModel
import javax.inject.Inject

class SplashRepository @Inject constructor(
    private val apiService: CoinApi,
    private val firebaseHelper: FirebaseHelper,
    private val context: Context
) {


    suspend fun signInFirebase() = firebaseHelper.signInFirebase()

    suspend fun checkApiStatus(
        iNetworkResponseHandling: INetworkResponseHandling
    ): DataHolder<ApiStatusResponseModel>? {
        return object : RequestHelper<ApiStatusResponseModel>() {
            override suspend fun createNetworkRequest(): ApiStatusResponseModel {
                return apiService.checkApiStatus()
            }

        }.loadRequest(iNetworkResponseHandling, true, context)
    }
}

