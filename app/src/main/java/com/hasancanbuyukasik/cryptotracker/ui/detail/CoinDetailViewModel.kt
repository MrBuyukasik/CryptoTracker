package com.hasancanbuyukasik.cryptotracker.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.SetOptions
import com.hasancanbuyukasik.cryptotracker.base.BaseViewModel
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.DataHolder
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinDetailResponseModel
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel
import com.hasancanbuyukasik.cryptotracker.ui.coins.CoinListRepository
import com.hasancanbuyukasik.cryptotracker.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


class CoinDetailViewModel @Inject constructor(private val coinDetailRepository: CoinDetailRepository) :
    BaseViewModel() {

    var coinDetail: MutableLiveData<CoinDetailResponseModel> = MutableLiveData()
    val addFavoriteCoin: MutableLiveData<Resource<String>> = MutableLiveData()
    val deleteFavoriteCoin: MutableLiveData<Resource<String>> = MutableLiveData()


    fun getCoinById(
        coinId: String
    ) {
        viewModelScope.launch {
            val response =
                coinDetailRepository.getCoinById(
                    coinId,
                    this@CoinDetailViewModel
                )

            response.let {
                if (it is DataHolder.Success) {
                    coinDetail.postValue(it.body)
                }
            }
        }
    }

    suspend fun addToFavoriteCoins(coin: HashMap<String, String>) {
        //  addFavoriteCoin.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val collectionReference = coinDetailRepository.saveFavoriteCoin()
                collectionReference.document(coin["id"]!!)
                    .set(coin, SetOptions.merge())
                    .addOnSuccessListener {
                        addFavoriteCoin.postValue(Resource.Success("Coin added to favorite"))
                    }
                    .addOnFailureListener {
                        addFavoriteCoin.postValue(Resource.Error("An error occurred: ${it.message}"))
                    }
            } catch (t: Throwable) {
                addFavoriteCoin.postValue(Resource.Error("An error occurred: ${t.message}"))
            }
        }

    }

    suspend fun deleteFavoriteCoin(coin: HashMap<String, String>) {

        viewModelScope.launch {
            deleteFavoriteCoin.postValue(Resource.Loading())
            try {
                val collectionReference = coinDetailRepository.deleteFavoriteCoin()
                collectionReference.document(coin["id"]!!)
                    .delete()
                    .addOnSuccessListener {
                        deleteFavoriteCoin.postValue(Resource.Success("Coin deleted from favorite"))
                    }
                    .addOnFailureListener {
                        deleteFavoriteCoin.postValue(Resource.Error("An error occurred: ${it.message}"))
                    }
            } catch (t: Throwable) {
                deleteFavoriteCoin.postValue(Resource.Error("An error occurred: ${t.message}"))
            }
        }

    }
}