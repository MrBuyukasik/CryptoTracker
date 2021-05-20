package com.hasancanbuyukasik.cryptotracker.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer

import com.hasancanbuyukasik.cryptotracker.R
import com.hasancanbuyukasik.cryptotracker.base.BaseFragment
import com.hasancanbuyukasik.cryptotracker.databinding.FragmentSplashBinding
import com.hasancanbuyukasik.cryptotracker.di.scope.Injectable
import com.hasancanbuyukasik.cryptotracker.ui.MainActivity
import com.hasancanbuyukasik.cryptotracker.utils.Resource

class SplashFragment :
    BaseFragment<SplashViewModel, FragmentSplashBinding>(), Injectable {
    override val getLayoutId: Int = R.layout.fragment_splash
    override val viewModelClass = SplashViewModel::class.java


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        signInFirebase()
    }

    private fun init() {
        observe()
    }

    private fun signInFirebase() {
        viewModel.signInFirebase()
    }

    private fun observe() {

        //this is for firebase auth via email and password
        viewModel.firebaseUser.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.checkApiStatus()
                }
                Resource.Status.ERROR -> {
                    //loadingView.visibility = View.GONE
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setCancelable(false)
                        .setPositiveButton("Try Again") { dialog, _ ->
                            dialog.dismiss()
                            viewModel.signInFirebase()
                        }.show()
                }
                Resource.Status.LOADING -> {
                    //   loadingView.visibility = View.VISIBLE
                }
            }
        })

        viewModel.apiStatus.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    //loadingView.visibility = View.GONE
                    //loadingTextView.visibility = View.GONE
                    navigateMainActivity()
                }
                Resource.Status.ERROR -> {
                    //    loadingView.visibility = View.GONE
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setCancelable(false)
                        .setPositiveButton("Try Again") { dialog, _ ->
                            dialog.dismiss()
                            viewModel.checkApiStatus()
                        }.show()
                }
                Resource.Status.LOADING -> {
                    //   loadingView.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun navigateMainActivity() {
        Handler().postDelayed({
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }, 500)
    }
}