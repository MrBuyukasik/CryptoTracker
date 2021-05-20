package com.hasancanbuyukasik.cryptotracker.ui.coins.adapters

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.hasancanbuyukasik.cryptotracker.R
import com.hasancanbuyukasik.cryptotracker.binding.DataBoundAdapter
import com.hasancanbuyukasik.cryptotracker.binding.DataBoundViewHolder
import com.hasancanbuyukasik.cryptotracker.databinding.CoinListItemBinding
import com.hasancanbuyukasik.cryptotracker.interfaces.ICoins
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel

class CoinListAdapter(
    private val coinList: ArrayList<CoinResponseModel>,
    private val ICoins: ICoins
) : DataBoundAdapter<CoinListItemBinding>(R.layout.coin_list_item) {

    private var lastPosition: Int = -1


    override fun bindItem(
        holder: DataBoundViewHolder<CoinListItemBinding>,
        position: Int,
        payloads: List<Any>
    ) {


        holder.binding.data = coinList[position]
        holder.binding.callback = ICoins

        coinList[position].symbol

        val animation: Animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top
        )

        holder.itemView.startAnimation(animation)
        lastPosition = position

    }

    private val differCallback = object : DiffUtil.ItemCallback<CoinResponseModel>() {
        override fun areItemsTheSame(
            oldItem: CoinResponseModel,
            newItem: CoinResponseModel
        ): Boolean {
            return oldItem.coinId == newItem.coinId
        }

        override fun areContentsTheSame(
            oldItem: CoinResponseModel,
            newItem: CoinResponseModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return coinList.size
    }

}