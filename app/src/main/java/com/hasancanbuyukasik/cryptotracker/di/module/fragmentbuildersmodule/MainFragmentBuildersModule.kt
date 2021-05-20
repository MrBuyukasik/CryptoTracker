package com.hasancanbuyukasik.cryptotracker.di.module.fragmentbuildersmodule


import com.hasancanbuyukasik.cryptotracker.ui.coins.CoinListFragment
import com.hasancanbuyukasik.cryptotracker.ui.detail.CoinDetailFragment
import com.hasancanbuyukasik.cryptotracker.ui.favorite.FavoriteCoinsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun coinListFragment(): CoinListFragment

    @ContributesAndroidInjector
    abstract fun coinDetailFragment(): CoinDetailFragment

    @ContributesAndroidInjector
    abstract fun favoriteCoinsFragment(): FavoriteCoinsFragment
}
