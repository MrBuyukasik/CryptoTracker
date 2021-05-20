package com.hasancanbuyukasik.cryptotracker.ui.detail

import android.app.Activity
import android.os.Bundle
import android.text.Html
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.hasancanbuyukasik.cryptotracker.R
import com.hasancanbuyukasik.cryptotracker.base.BaseFragment
import com.hasancanbuyukasik.cryptotracker.databinding.FragmentCoinDetailBinding
import com.hasancanbuyukasik.cryptotracker.models.coin.CoinDetailResponseModel
import com.hasancanbuyukasik.cryptotracker.ui.MainActivity
import com.hasancanbuyukasik.cryptotracker.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoinDetailFragment :
    BaseFragment<CoinDetailViewModel, FragmentCoinDetailBinding>() {

    override val getLayoutId: Int =
        com.hasancanbuyukasik.cryptotracker.R.layout.fragment_coin_detail
    override val viewModelClass = CoinDetailViewModel::class.java
    private val args: CoinDetailFragmentArgs by navArgs()
    private var isCharacterFavorite = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        backPress()
        initAddFavorite()
        initIntervalChange()
        observe()
    }

    private fun initData() {
        viewModel.getCoinById(args.coinResponse.coinId)
        if (args.fromFavoriteCoinsFragment) {
            isCharacterFavorite = true
            binding.coinDetailAddFavoriteButton.setBackgroundResource(R.drawable.ic_favorited)
        } else {
            isCharacterFavorite = false
            binding.coinDetailAddFavoriteButton.setBackgroundResource(R.drawable.ic_favorite_default)
        }
    }

    private fun observe() {
        //api return a coin detail and update coin detail
        viewModel.coinDetail.observe(viewLifecycleOwner, Observer {
            setCoinDetail(it)
        })


        // if user click on add favorite fab than add clicked coin to firebase firestore
        viewModel.addFavoriteCoin.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    hideLoading()
                    Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT).show()
                    binding.coinDetailAddFavoriteButton.setBackgroundResource(R.drawable.ic_favorited)
                    isCharacterFavorite = true
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setPositiveButton("Try Again") { dialog, _ ->
                            dialog.dismiss()
                            addToFavoriteCoins()
                        }.show()
                }
                Resource.Status.LOADING -> {
                    showLoading()
                }
            }
        })


        // if user come from favorite coins fragment and click on add favorite fab than delete clicked coin from firebase firestore
        viewModel.deleteFavoriteCoin.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    hideLoading()
                    Toast.makeText(requireContext(), response.data, Toast.LENGTH_SHORT).show()
                    binding.coinDetailAddFavoriteButton.setBackgroundResource(R.drawable.ic_favorite_default)
                    isCharacterFavorite = false
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    AlertDialog.Builder(requireContext())
                        .setMessage(response.message)
                        .setPositiveButton(getString(R.string.try_again)) { dialog, _ ->
                            dialog.dismiss()
                            deleteFromFavoriteCoins()
                        }.show()
                }
                Resource.Status.LOADING -> {
                    showLoading()
                }
            }
        })


    }

    private fun initIntervalChange() {
        binding.coinDetailRefreshIntervalDone.setOnClickListener {

            val interval = binding.coinDetailRefreshInterval.text.toString().toIntOrNull()

            if (binding.coinDetailRefreshInterval.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.enter_interval_value),
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (interval != null) {
                if (interval != 0) {
                    setRefreshInterval(interval)
                    Toast.makeText(
                        requireContext(),
                        "Refresh interval set to $interval minute",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.coinDetailRefreshInterval.clearFocus()

                    val inputMethodManager: InputMethodManager =
                        requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.invalid_value),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }

    private fun setRefreshInterval(interval: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                viewModel.getCoinById(args.coinResponse.coinId)
                val delay = interval * 60000
                delay(delay.toLong())
            }
        }
    }

    private fun initAddFavorite() {
        binding.coinDetailAddFavoriteButton.setOnClickListener {

            if (args.fromFavoriteCoinsFragment || isCharacterFavorite) {
                deleteFromFavoriteCoins()

            } else {
                addToFavoriteCoins()
            }
        }
    }

    private fun setCoinDetail(coinDetailResponse: CoinDetailResponseModel) {
        binding.coinDetailName.text = args.coinResponse.name
        Glide.with(requireContext()).load(coinDetailResponse.image?.imageLarge)
            .into(binding.coinImage)

        if (coinDetailResponse.hashingAlgorithm != null)
            binding.coinDetailHashAlgorithmText.text = coinDetailResponse.hashingAlgorithm
        else
            binding.coinDetailHashAlgorithmText.text = getString(R.string.no_data)

        if (coinDetailResponse.description?.descriptionEn != null && coinDetailResponse.description.descriptionEn != "") {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                binding.coinDetailDescriptionText.text = Html.fromHtml(
                    coinDetailResponse.description.descriptionEn,
                    Html.FROM_HTML_MODE_COMPACT
                )
            } else {
                binding.coinDetailDescriptionText.text =
                    Html.fromHtml(coinDetailResponse.description.descriptionEn)
            }
        } else {
            binding.coinDetailDescriptionText.text = getString(R.string.no_data)
        }

        if (coinDetailResponse.marketData?.currentPrice?.usd != null)
            binding.coinDetailPriceText.text =
                coinDetailResponse.marketData.currentPrice.usd.toString()
        else
            binding.coinDetailPriceText.text = getString(R.string.no_data)

        if (coinDetailResponse.marketData?.priceChancePercentage24h != null)
            binding.coinDetailPriceChangeText.text =
                coinDetailResponse.marketData.priceChancePercentage24h.toString()
        else
            binding.coinDetailPriceChangeText.text = getString(R.string.no_data)
    }

    private fun addToFavoriteCoins() {
        val coin = hashMapOf(
            "id" to args.coinResponse.coinId,
            "name" to args.coinResponse.name,
            "symbol" to args.coinResponse.symbol
        )
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addToFavoriteCoins(coin)
        }
    }

    private fun deleteFromFavoriteCoins() {
        val coin = hashMapOf(
            "id" to args.coinResponse.coinId,
            "name" to args.coinResponse.name,
            "symbol" to args.coinResponse.symbol
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.deleteFavoriteCoin(coin)
        }
    }

    private fun backPress() {
        binding.toolbarBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_coinDetailFragment_to_coinListFragment
            )
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(
                        R.id.action_coinDetailFragment_to_coinListFragment
                    )
                }
            })
    }
}