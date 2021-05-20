package com.hasancanbuyukasik.cryptotracker.di.module.fragmentbuildersmodule

import com.hasancanbuyukasik.cryptotracker.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment
}