package com.hasancanbuyukasik.cryptotracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hasancanbuyukasik.cryptotracker.R
import com.hasancanbuyukasik.cryptotracker.base.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
