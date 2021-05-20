package com.hasancanbuyukasik.cryptotracker.models.coin

import com.google.gson.annotations.SerializedName


data class CoinDescriptionModel(
    @SerializedName("en")
    val descriptionEn: String?
)