package com.hasancanbuyukasik.cryptotracker.binding

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding


abstract class DataBoundAdapter<T : ViewDataBinding>
    (@param:LayoutRes @field:LayoutRes private val mLayoutId: Int) : BaseDataBoundAdapter<T>() {
    override fun getItemLayoutId(position: Int): Int {
        return mLayoutId
    }

}
