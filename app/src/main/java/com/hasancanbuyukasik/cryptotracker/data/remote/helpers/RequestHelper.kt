package com.hasancanbuyukasik.cryptotracker.data.remote.helpers

import android.content.Context
import androidx.annotation.MainThread
import com.hasancanbuyukasik.cryptotracker.data.remote.INetworkResponseHandling
import com.hasancanbuyukasik.cryptotracker.utils.Utils
import java.lang.Exception

abstract class RequestHelper<T> {

    suspend fun loadRequest(
        iNetworkResponseHandling: INetworkResponseHandling,
        isNeedProgressBar: Boolean,
        context: Context
    ): DataHolder<T>? {

        if (isNeedProgressBar) iNetworkResponseHandling.loading(true)
        return try {
            if (Utils.hasInternetConnection(context)) {
                val response = createNetworkRequest()
                if (isNeedProgressBar) iNetworkResponseHandling.loading(false)
                DataHolder.Success(response)
            } else {
                DataHolder.Error("No internet connection")
            }
        } catch (e: Exception) {
            iNetworkResponseHandling.onErrorPopUp("Error", "An error occurred on network layer")
            DataHolder.Error("An error occurred on network layer")
        }
    }

    @MainThread
    protected abstract suspend fun createNetworkRequest(): T
}