package com.hasancanbuyukasik.cryptotracker.binding

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.OnRebindCallback
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseDataBoundAdapter<T : ViewDataBinding> : RecyclerView.Adapter<DataBoundViewHolder<T>>() {
    private var mRecyclerView: RecyclerView? = null

    /**
     * This is used to block items from updating themselves. RecyclerView wants to know when an
     * item is invalidated and it prefers to refresh it via onRebind. It also helps with performance
     * since data binding will not update views that are not changed.
     */
    private val mOnRebindCallback = object : OnRebindCallback<T>() {
        override fun onPreBind(binding: T): Boolean {
            if (mRecyclerView == null || mRecyclerView!!.isComputingLayout) {
                return true
            }
            val childAdapterPosition = mRecyclerView!!.getChildAdapterPosition(binding.root)
            if (childAdapterPosition == RecyclerView.NO_POSITION) {
                return true
            }
            notifyItemChanged(childAdapterPosition, DB_PAYLOAD)
            return false
        }
    }

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<T> {
        val vh = DataBoundViewHolder.create<T>(parent, viewType)
        vh.binding.addOnRebindCallback(mOnRebindCallback)
        return vh
    }

    override fun onBindViewHolder(
        holder: DataBoundViewHolder<T>, position: Int,
        payloads: List<Any>
    ) {
        // when a VH is rebound to the same item, we don't have to call the setters
        if (payloads.isEmpty() || hasNonDataBindingInvalidate(payloads)) {
            bindItem(holder, position, payloads)
        }
        holder.binding.executePendingBindings()
    }

    protected abstract fun bindItem(
        holder: DataBoundViewHolder<T>, position: Int,
        payloads: List<Any>
    )

    private fun hasNonDataBindingInvalidate(payloads: List<Any>): Boolean {
        for (payload in payloads) {
            if (payload !== DB_PAYLOAD) {
                return true
            }
        }
        return false
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<T>, position: Int) {
        throw IllegalArgumentException("just overridden to make final.")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = null
    }

    override fun getItemViewType(position: Int): Int {
        return getItemLayoutId(position)
    }

    @LayoutRes
    abstract fun getItemLayoutId(position: Int): Int

    companion object {
        private val DB_PAYLOAD = Any()
    }
}
