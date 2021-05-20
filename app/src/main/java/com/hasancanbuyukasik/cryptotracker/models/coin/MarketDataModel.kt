package com.hasancanbuyukasik.cryptotracker.models.coin

import com.google.gson.annotations.SerializedName
import com.hasancanbuyukasik.cryptotracker.models.coin.CurrentPriceModel

data class MarketDataModel(
    @SerializedName("current_price")
    val currentPrice: CurrentPriceModel?,

    @SerializedName("price_change_percentage_24h")
    val priceChancePercentage24h: Double?
)