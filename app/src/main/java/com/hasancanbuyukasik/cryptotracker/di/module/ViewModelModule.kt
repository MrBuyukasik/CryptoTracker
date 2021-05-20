package com.hasancanbuyukasik.cryptotracker.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hasancanbuyukasik.cryptotracker.di.qualifiers.ViewModelKey
import com.hasancanbuyukasik.cryptotracker.ui.coins.CoinListViewModel
import com.hasancanbuyukasik.cryptotracker.ui.detail.CoinDetailViewModel
import com.hasancanbuyukasik.cryptotracker.ui.favorite.FavoriteCoinsViewModel
import com.hasancanbuyukasik.cryptotracker.ui.splash.SplashViewModel
import com.hasancanbuyukasik.cryptotracker.viewmodel.ViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CoinListViewModel::class)
    abstract fun bindCoinListViewModel(CoinListViewModel: CoinListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CoinDetailViewModel::class)
    abstract fun bindCoinDetailViewModel(coinDetailViewModel: CoinDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteCoinsViewModel::class)
    abstract fun bindFavoriteCoinsViewModel(favoriteCoinsViewModel: FavoriteCoinsViewModel): ViewModel

}