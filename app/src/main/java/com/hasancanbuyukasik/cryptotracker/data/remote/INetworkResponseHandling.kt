package com.hasancanbuyukasik.cryptotracker.data.remote

interface INetworkResponseHandling {
    fun loading(switch: Boolean)
    fun onGenericErrorTaken(message: String)
    fun onErrorPopUp(header: String, body: String)
}