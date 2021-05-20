package com.hasancanbuyukasik.cryptotracker.base

import androidx.lifecycle.ViewModel
import com.hasancanbuyukasik.cryptotracker.data.remote.INetworkResponseHandling
import com.hasancanbuyukasik.cryptotracker.models.error.ErrorPopUpMessage
import com.hasancanbuyukasik.cryptotracker.utils.SingleLiveEvent

open class BaseViewModel : ViewModel(), INetworkResponseHandling {
    val errorMessage: SingleLiveEvent<ErrorPopUpMessage> = SingleLiveEvent()

    val loading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    //region interface
    override fun loading(switch: Boolean) {
        loading.postValue(switch)
    }

    override fun onGenericErrorTaken(message: String) {
        errorMessage.postValue(ErrorPopUpMessage("error", message))
    }

    override fun onErrorPopUp(header: String, body: String) {
        errorMessage.postValue(ErrorPopUpMessage(header, body))
    }
    //endregion

}