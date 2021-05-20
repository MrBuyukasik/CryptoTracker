package com.hasancanbuyukasik.cryptotracker.ui.coins

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hasancanbuyukasik.cryptotracker.base.BaseFragment
import com.hasancanbuyukasik.cryptotracker.R
import com.hasancanbuyukasik.cryptotracker.databinding.FragmentCoinListBinding
import com.hasancanbuyukasik.cryptotracker.interfaces.ICoins
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinResponseModel
import com.hasancanbuyukasik.cryptotracker.ui.coins.adapters.CoinListAdapter
import com.hasancanbuyukasik.cryptotracker.utils.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoinListFragment :
    BaseFragment<CoinListViewModel, FragmentCoinListBinding>(), ICoins {


    override val getLayoutId: Int = R.layout.fragment_coin_list
    override val viewModelClass = CoinListViewModel::class.java

    private val coinList: ArrayList<CoinResponseModel> = arrayListOf()
    private val filteredCoinList: ArrayList<CoinResponseModel> = arrayListOf()
    private var coinListAdapter = CoinListAdapter(coinList, this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initData()
        observe()
        initRecyclerView(coinList)
        initFavoriteButton()
        initSearchView()
    }


    private fun initData() {
        viewModel.getCoinsList()
    }

    private fun observe() {
        viewModel.coinList.observe(viewLifecycleOwner, Observer {
            coinList.clear()
            coinList.addAll(it)
            initRecyclerView(coinList)
            coinListAdapter.differ.submitList(it)
            binding.recyclerCoins.scheduleLayoutAnimation()
            GlobalScope.launch {
                viewModel.insertCoinList(it)
            }
        })

        viewModel.filteredCoinList.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    hideLoading()
                    filteredCoinList.clear()
                    response.data?.let { filteredCoinList.addAll(it) }
                    initRecyclerView(filteredCoinList)
                    binding.recyclerCoins.scheduleLayoutAnimation()
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setCancelable(false)
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
                Resource.Status.LOADING -> {
                    showLoading()
                }
            }

        })


    }

    private fun initRecyclerView(coinList: ArrayList<CoinResponseModel>) {

        coinListAdapter = CoinListAdapter(coinList, this)

        val layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(
                requireContext(),
                R.anim.layout_animation_fall_down
            )

        binding.recyclerCoins.apply {
            adapter = coinListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            layoutAnimation = layoutAnimationController
        }
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.filterCoinList(query)
                    binding.recyclerCoins.scheduleLayoutAnimation()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    initRecyclerView(coinList)
                }
                return true
            }


        })
    }

    private fun initFavoriteButton() {
        binding.favoriteCoinNavigateButton.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                findNavController().navigate(R.id.action_coinListFragment_to_favoriteCoinsFragment)
            }
        }
    }

    override fun onCoinClicked(coinResponseModel: CoinResponseModel) {
        val bundle = Bundle().apply {
            putSerializable("coinResponse", coinResponseModel)
            putBoolean("fromFavoriteCoinsFragment", false)
        }

        findNavController().navigate(
            R.id.action_coinListFragment_to_coinDetailFragment,
            bundle
        )
    }
}
