package com.hasancanbuyukasik.cryptotracker.di.scope

import android.app.Application
import com.hasancanbuyukasik.cryptotracker.CryptoTrackerApplication
import com.hasancanbuyukasik.cryptotracker.di.module.ActivityModule
import com.hasancanbuyukasik.cryptotracker.di.module.AppModule
import com.hasancanbuyukasik.cryptotracker.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        NetworkModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(initApplication: CryptoTrackerApplication)
}