package com.hasancanbuyukasik.cryptotracker.ui.coins

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.google.firebase.firestore.SetOptions
import com.hasancanbuyukasik.cryptotracker.base.BaseViewModel
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.DataHolder
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel
import com.hasancanbuyukasik.cryptotracker.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinListViewModel @Inject constructor(private val coinListRepository: CoinListRepository) :
    BaseViewModel() {

    var coinList: MutableLiveData<List<CoinResponseModel>> = MutableLiveData()
    val filteredCoinList: MutableLiveData<Resource<List<CoinResponseModel>>> = MutableLiveData()
    val insertCoinList: MutableLiveData<Resource<List<Long>>> = MutableLiveData()


    fun getCoinsList(
    ) {
        viewModelScope.launch {
            val response =
                coinListRepository.getCoinList(
                    this@CoinListViewModel
                )

            response.let {
                if (it is DataHolder.Success) {
                    coinList.postValue(it.body)
                }
            }
        }
    }

    fun filterCoinList(query: String) {
        //filteredCoinList.postValue(Resource.Loading())

        viewModelScope.launch {
            try {
                val filterSQLiteQuery =
                    SimpleSQLiteQuery("SELECT * FROM Coins WHERE coinName LIKE '%$query%' OR coinSymbol LIKE '%$query%'")
                val result = coinListRepository.searchCoinByNameOrSymbol(filterSQLiteQuery)
                if (result.isNotEmpty())
                    filteredCoinList.postValue(Resource.Success(result))
                else
                    filteredCoinList.postValue(Resource.Error("Coins not found"))
            } catch (t: Throwable) {
                filteredCoinList.postValue(Resource.Error("An error occurred, please try again: ${t.message}"))
            }
        }
    }

    fun insertCoinList(coinList: List<CoinResponseModel>) {
        viewModelScope.launch {
            insertCoinList.postValue(Resource.Loading())
            try {
                val result = coinListRepository.insertCoinListLocalDatabase(coinList)
                if (result.isNotEmpty())
                    insertCoinList.postValue(Resource.Success(result))
                else
                    insertCoinList.postValue(Resource.Error("An error occurred, please try again"))
            } catch (t: Throwable) {
                insertCoinList.postValue(Resource.Error("An error occurred, please try again: ${t.message}"))
            }
        }
    }
}