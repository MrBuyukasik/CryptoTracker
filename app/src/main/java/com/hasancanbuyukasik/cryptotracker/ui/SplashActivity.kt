package com.hasancanbuyukasik.cryptotracker.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.hasancanbuyukasik.cryptotracker.R
import com.hasancanbuyukasik.cryptotracker.base.BaseActivity
import com.hasancanbuyukasik.cryptotracker.ui.splash.SplashFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class SplashActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container_layout, SplashFragment())
        fragmentTransaction.commit()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

}
