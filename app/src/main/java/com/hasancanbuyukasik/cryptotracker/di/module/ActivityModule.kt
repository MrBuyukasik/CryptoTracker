package com.hasancanbuyukasik.cryptotracker.di.module

import com.hasancanbuyukasik.cryptotracker.ui.MainActivity
import com.hasancanbuyukasik.cryptotracker.di.module.fragmentbuildersmodule.MainFragmentBuildersModule
import com.hasancanbuyukasik.cryptotracker.di.module.fragmentbuildersmodule.SplashFragmentBuildersModule
import com.hasancanbuyukasik.cryptotracker.ui.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class])
    abstract fun contributeHomeActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SplashFragmentBuildersModule::class])
    abstract fun contributeSplashActivity(): SplashActivity
}