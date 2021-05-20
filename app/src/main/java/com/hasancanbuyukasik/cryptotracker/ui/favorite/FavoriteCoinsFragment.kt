package com.hasancanbuyukasik.cryptotracker.ui.favorite

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hasancanbuyukasik.cryptotracker.R
import com.hasancanbuyukasik.cryptotracker.base.BaseFragment
import com.hasancanbuyukasik.cryptotracker.base.BaseViewModel
import com.hasancanbuyukasik.cryptotracker.databinding.FragmentCoinDetailBinding
import com.hasancanbuyukasik.cryptotracker.databinding.FragmentFavoriteCoinsBinding
import com.hasancanbuyukasik.cryptotracker.interfaces.ICoins
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel
import com.hasancanbuyukasik.cryptotracker.ui.MainActivity
import com.hasancanbuyukasik.cryptotracker.ui.coins.adapters.CoinListAdapter
import com.hasancanbuyukasik.cryptotracker.ui.detail.CoinDetailViewModel
import com.hasancanbuyukasik.cryptotracker.utils.Resource
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class FavoriteCoinsFragment :
    BaseFragment<FavoriteCoinsViewModel, FragmentFavoriteCoinsBinding>(), ICoins {

    override val getLayoutId: Int =
        com.hasancanbuyukasik.cryptotracker.R.layout.fragment_favorite_coins
    override val viewModelClass = FavoriteCoinsViewModel::class.java
    private val favoriteCoins: ArrayList<CoinResponseModel> = arrayListOf()
    private var coinListAdapter = CoinListAdapter(favoriteCoins, this)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initData()
        observe()
        initRecyclerView(favoriteCoins)
    }


    private fun initData() {
        viewModel.getFavoriteCoins()
    }


    private fun observe() {
        //if firebase firestore return a coin list than show that list via recycler view
        viewModel.favoriteCoins.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    if (response.data.isNullOrEmpty()) {
                        binding.recyclerFavoriteCoinList.visibility = View.GONE
                        binding.tvEmptyList.visibility = View.VISIBLE
                    } else {
                        binding.recyclerFavoriteCoinList.visibility = View.VISIBLE
                        binding.tvEmptyList.visibility = View.GONE
                        initRecyclerView(ArrayList(response.data))
                        binding.recyclerFavoriteCoinList.scheduleLayoutAnimation()
                    }
                }
                Resource.Status.ERROR -> {
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
                            dialog.dismiss()
                            viewModel.getFavoriteCoins()
                        }.setNegativeButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
                Resource.Status.LOADING -> {
                }
            }
        })
    }


    private fun initRecyclerView(coinList: ArrayList<CoinResponseModel>) {
        coinListAdapter = CoinListAdapter(coinList, this)
        val layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_fall_down)
        binding.recyclerFavoriteCoinList.apply {
            adapter = coinListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            layoutAnimation = layoutAnimationController
        }
    }


    override fun onCoinClicked(coinResponseModel: CoinResponseModel) {
        val bundle = Bundle().apply {
            putSerializable("coinResponse", coinResponseModel)
            putBoolean("fromFavoriteCoinsFragment", true)
        }

        findNavController().navigate(
            R.id.action_favoriteCoinsFragment_to_coinDetailFragment,
            bundle
        )
    }
}