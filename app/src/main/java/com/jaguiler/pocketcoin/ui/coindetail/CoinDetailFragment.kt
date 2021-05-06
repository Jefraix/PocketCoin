package com.jaguiler.pocketcoin.ui.coindetail

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.jaguiler.pocketcoin.R
import com.jaguiler.pocketcoin.databinding.CoinDetailFragmentBinding
import com.jaguiler.pocketcoin.ui.api.Coin
import com.jaguiler.pocketcoin.ui.watchlist.WatchlistViewModel

class CoinDetailFragment : Fragment() {

    private var binding: CoinDetailFragmentBinding? = null
    private val viewModel: WatchlistViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailBinding = CoinDetailFragmentBinding.inflate(inflater, container, false)
        binding = detailBinding

        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            viewModel.selectedCoin.observe(viewLifecycleOwner, {
                populateInfo(it)
            })

        }

    }

    private fun populateInfo(coin: Coin) {
        binding?.apply {

            detCoinnameTextView.text = coin.name
            detCoinacrTextView.text = coin.symbol

            val price = "$" + String.format("%,f", coin.price_usd)
            detPriceusdTextView.text = price

            val icon = when(coin.id) {
                90 -> R.drawable.bitcoin_sym
                80 -> R.drawable.ethereum_sym
                2710 -> R.drawable.binance_coin_sym
                2 -> R.drawable.dogecoin_sym
                58 -> R.drawable.xrp_sym
                257 -> R.drawable.cardano_sym
                45219 -> R.drawable.polkadot_sym
                1 -> R.drawable.litecoin_sym
                2321 -> R.drawable.bitcoin_cash_sym
                2751 -> R.drawable.chainlink_sym
                47305 -> R.drawable.uniswap_sym
                89 -> R.drawable.stellar_sym
                2741 -> R.drawable.vechain_sym
                32360 -> R.drawable.theta_token_sym
                2713 -> R.drawable.tron_sym
                118 -> R.drawable.ethereum_classic
                28 -> R.drawable.monero_sym
                33422 -> R.drawable.wrapped_bitcoin_sym
                133 -> R.drawable.neo_sym
                2679 -> R.drawable.eos_sym
                32607 -> R.drawable.filecoin_sym
                47311 -> R.drawable.iou_sym

                else -> R.drawable.ic_outline_info_24
            }
            detCoiniconImageView.setImageResource(icon)

            detCoinrankValTextView.text = coin.rank.toString()

            val hColor = if (coin.percent_change_1h < 0.0) Color.RED else Color.GREEN
            val dColor = if (coin.percent_change_24h < 0.0) Color.RED else Color.GREEN
            val wColor = if (coin.percent_change_7d < 0.0) Color.RED else Color.GREEN

            detOnehValTextView.text = coin.percent_change_1h.toBigDecimal().toPlainString()
            detTwentyfourhValTextView.text = coin.percent_change_24h.toBigDecimal().toPlainString()
            detWeekValTextView.text = coin.percent_change_7d.toBigDecimal().toPlainString()

            detOnehValTextView.setTextColor(hColor)
            detTwentyfourhValTextView.setTextColor(dColor)
            detWeekValTextView.setTextColor(wColor)

            val cap = "$" + String.format("%,.2f", coin.market_cap_usd)
            detMarketcapValTextView.text = cap
            val vol = "$" + String.format("%,.2f", coin.volume24)
            detDayVolumeValTextView.text = vol
            val csupply = "$" + String.format("%,.0f", coin.csupply)
            detCsupplyValTextView.text = csupply
            val tsupply = "$" + String.format("%,.0f", coin.tsupply)
            detTsupplyValTextView.text = tsupply

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}