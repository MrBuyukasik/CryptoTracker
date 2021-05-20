package com.hasancanbuyukasik.cryptotracker.data.remote

import com.hasancanbuyukasik.cryptotracker.models.api.ApiStatusResponseModel
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinDetailResponseModel
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {

    @GET("v3/coins/list")
    suspend fun getCoinList(): List<CoinResponseModel>

    @GET("v3/coins/{id}")
    suspend fun getCoinDetail(@Path("id") id: String): CoinDetailResponseModel

    @GET("v3/ping")
    suspend fun checkApiStatus(): ApiStatusResponseModel
}