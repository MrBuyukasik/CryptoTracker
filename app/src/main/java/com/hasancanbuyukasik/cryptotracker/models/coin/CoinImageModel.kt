package com.hasancanbuyukasik.cryptotracker.models.coin

import com.google.gson.annotations.SerializedName

data class CoinImageModel(
    @SerializedName("large")
    val imageLarge: String?
)