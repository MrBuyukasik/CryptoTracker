package com.hasancanbuyukasik.cryptotracker.models.api

import java.io.Serializable

data class ApiStatusResponseModel(
    val gecko_says: String
) : Serializable {
    override fun toString(): String {
        return "ApiStatusResponse(gecko_says='$gecko_says')"
    }
}