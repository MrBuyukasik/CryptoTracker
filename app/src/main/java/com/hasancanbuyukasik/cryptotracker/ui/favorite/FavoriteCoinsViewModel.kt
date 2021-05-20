package com.hasancanbuyukasik.cryptotracker.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hasancanbuyukasik.cryptotracker.base.BaseViewModel
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel
import com.hasancanbuyukasik.cryptotracker.ui.detail.CoinDetailRepository
import com.hasancanbuyukasik.cryptotracker.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteCoinsViewModel @Inject constructor(private val favoriteCoinsRepository: FavoriteCoinsRepository) :
    BaseViewModel() {

    val favoriteCoins: MutableLiveData<Resource<List<CoinResponseModel>>> = MutableLiveData()


    fun getFavoriteCoins() {
        viewModelScope.launch {
            // getFavoriteCoins.postValue(Resource.Loading())
            try {
                val collectionReference = favoriteCoinsRepository.getFavoriteCoins()
                collectionReference.get()
                    .addOnSuccessListener { result ->
                        if (result.isEmpty) {
                            favoriteCoins.postValue(Resource.Error("Favorite coins are empty"))
                        } else {
                            val coinList = ArrayList<CoinResponseModel>()
                            for (snapShot in result) {
                                val id = snapShot.data["id"].toString()
                                val name = snapShot.data["name"].toString()
                                val symbol = snapShot.data["symbol"].toString()

                                val coinResponse = CoinResponseModel(id, symbol, name)
                                coinList.add(coinResponse)
                            }
                            favoriteCoins.postValue(Resource.Success(coinList.toList()))

                        }
                    }
                    .addOnFailureListener {
                        favoriteCoins.postValue(Resource.Error("An error occurred: ${it.message}"))
                    }
            } catch (t: Throwable) {
                favoriteCoins.postValue(Resource.Error("An error occurred: ${t.message}"))
            }
        }

    }

}