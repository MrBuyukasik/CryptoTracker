package com.hasancanbuyukasik.cryptotracker.interfaces

import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel

interface ICoins {
    fun onCoinClicked(coinResponseModel: CoinResponseModel)
}