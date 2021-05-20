package com.hasancanbuyukasik.cryptotracker.ui.splash


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.hasancanbuyukasik.cryptotracker.base.BaseViewModel
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.DataHolder
import com.hasancanbuyukasik.cryptotracker.models.api.ApiStatusResponseModel
import com.hasancanbuyukasik.cryptotracker.utils.Resource
import com.hasancanbuyukasik.cryptotracker.utils.Utils
import kotlinx.coroutines.launch

import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val splashRepository: SplashRepository,
    private val context: Context
) :
    BaseViewModel() {


    val firebaseUser: MutableLiveData<Resource<FirebaseUser>> = MutableLiveData()
    val apiStatus: MutableLiveData<Resource<ApiStatusResponseModel>> = MutableLiveData()


    fun signInFirebase() = viewModelScope.launch {
        safeSignInFirebaseCall()
    }

    private suspend fun safeSignInFirebaseCall() {
        try {
            if (Utils.hasInternetConnection(context)) {
                val authResult = splashRepository.signInFirebase()
                authResult.addOnFailureListener {
                    firebaseUser.postValue(Resource.Error("An error occurred: ${it.message}"))
                }
                authResult.addOnCompleteListener { result ->
                    if (result.isSuccessful && result.result != null) {
                        result.result?.let { authResponse ->
                            if (authResponse.user != null) {
                                firebaseUser.postValue(Resource.Success(authResponse.user!!))
                            } else
                                firebaseUser.postValue(Resource.Error("Could not login to app, try again later"))
                        }
                    }
                }
            } else {
                firebaseUser.postValue(Resource.Error("No internet connection"))
            }


        } catch (t: Throwable) {
            firebaseUser.postValue(Resource.Error("An error occurred: ${t.message}"))
        }
    }

    fun checkApiStatus(
    ) {
        viewModelScope.launch {

            val response =
                splashRepository.checkApiStatus(
                    this@SplashViewModel
                )
            response.let {
                if (it is DataHolder.Success) {
                    apiStatus.postValue(Resource.Success(it.body))
                } else if (it is DataHolder.Error) {
                    apiStatus.postValue(Resource.Error(it.errorMessage))
                }
            }
        }

    }
}


