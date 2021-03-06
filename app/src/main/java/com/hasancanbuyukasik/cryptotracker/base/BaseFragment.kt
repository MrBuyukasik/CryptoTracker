package com.hasancanbuyukasik.cryptotracker.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hasancanbuyukasik.cryptotracker.di.scope.Injectable
import com.hasancanbuyukasik.cryptotracker.BR
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment(), Injectable {
    private val loadingDialogFragment = LoadingDialogFragment()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: VM

    protected lateinit var binding: DB

    abstract val getLayoutId: Int

    abstract val viewModelClass: Class<VM>

    private lateinit var baseActivity: BaseActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId, container, false)
        baseActivity = activity as BaseActivity
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        observe()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
    }

    private fun observe() {
        viewModel.errorMessage.observe(this, Observer {
            showErrorDialog(it.title, it.message)
        })

        viewModel.loading.observe(this, Observer {
            it?.let {
                if (it) showLoading()
                else hideLoading()
            }
        })
    }

    protected fun showLoading() {
        fragmentManager?.let {
            if (!loadingDialogFragment.isVisible) {
                loadingDialogFragment.show(it, "loadingDialogFragment")
            }
        }
    }

    protected fun hideLoading() {
        fragmentManager?.let {
            loadingDialogFragment.dismiss()
        }
    }

    private fun showErrorDialog(title: String, errorMessage: String) {
        hideLoading()
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(errorMessage)
                .setNeutralButton(android.R.string.ok) { view, _ -> view.dismiss() }
                .show()
        }
    }
}