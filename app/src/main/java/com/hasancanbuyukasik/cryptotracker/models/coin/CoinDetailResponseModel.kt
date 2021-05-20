package com.hasancanbuyukasik.cryptotracker.models.coin

import com.google.gson.annotations.SerializedName
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinDescriptionModel
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinImageModel
import com.hasancanbuyukasik.cryptotracker.models.coin.MarketDataModel

data class CoinDetailResponseModel(

    @SerializedName("market_data")
    val marketData: MarketDataModel?,

    @SerializedName("image")
    val image: CoinImageModel?,

    @SerializedName("description")
    val description: CoinDescriptionModel?,

    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String?
)




