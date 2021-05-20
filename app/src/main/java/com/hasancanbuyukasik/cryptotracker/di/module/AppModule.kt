package com.hasancanbuyukasik.cryptotracker.di.module

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hasancanbuyukasik.cryptotracker.BuildConfig
import com.hasancanbuyukasik.cryptotracker.data.local.CoinDao
import com.hasancanbuyukasik.cryptotracker.data.local.CoinDatabase
import com.hasancanbuyukasik.cryptotracker.data.remote.CoinApi
import com.hasancanbuyukasik.cryptotracker.data.remote.helpers.FirebaseHelper
import com.hasancanbuyukasik.cryptotracker.di.qualifiers.BaseUrlQualifier
import com.hasancanbuyukasik.cryptotracker.di.scope.AppScope
import com.hasancanbuyukasik.cryptotracker.ui.coins.CoinListRepository
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    @AppScope
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @AppScope
    @BaseUrlQualifier
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @AppScope
    fun provideFirebaseHelper(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): FirebaseHelper = FirebaseHelper(firebaseAuth, firebaseFirestore)

    @Provides
    @AppScope
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @AppScope
    fun provideFirebaseDatabase(): FirebaseFirestore = FirebaseFirestore.getInstance()


    @AppScope
    @Provides
    fun provideCoinDatabase(appContext: Context) =
        CoinDatabase.getDatabase(appContext)

    @AppScope
    @Provides
    fun provideCoinDao(coinDatabase: CoinDatabase) = coinDatabase.coinDao()

    @AppScope
    @Provides
    fun provideCoinListRepository(
        coinApi: CoinApi,
        context: Context,
        coinDao: CoinDao
    ) =
        CoinListRepository(coinApi, context, coinDao)
}