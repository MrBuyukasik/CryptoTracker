package com.hasancanbuyukasik.cryptotracker.models.coin

import com.google.gson.annotations.SerializedName

data class CurrentPriceModel(
    @SerializedName("usd")
    val usd: Double?
)
