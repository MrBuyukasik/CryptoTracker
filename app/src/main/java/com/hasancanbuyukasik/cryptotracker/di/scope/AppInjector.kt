package com.hasancanbuyukasik.cryptotracker.di.scope

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.hasancanbuyukasik.cryptotracker.CryptoTrackerApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

//Structure that is build for making fragments injectable
object AppInjector {
    fun init(cryptoTrackerApplication: CryptoTrackerApplication) {
        DaggerAppComponent.builder().application(cryptoTrackerApplication)
            .build().inject(cryptoTrackerApplication)
        cryptoTrackerApplication.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivityDestroyed(activity: Activity) {}

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }
        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        //Fragmentlar'ın injectable hale getirildiği yer.
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }
}