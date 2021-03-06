package com.hasancanbuyukasik.cryptotracker.models.coin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Coins")
data class CoinResponseModel(
    @PrimaryKey @ColumnInfo(name = "coinId") @SerializedName("id")
    val coinId: String,

    @ColumnInfo(name = "coinSymbol") @SerializedName("symbol")
    val symbol: String,

    @ColumnInfo(name = "coinName") @SerializedName("name")
    val name: String
) : Serializable {
    override fun toString(): String {
        return "CoinResponse(id='$coinId', symbol='$symbol', name='$name')"
    }
}