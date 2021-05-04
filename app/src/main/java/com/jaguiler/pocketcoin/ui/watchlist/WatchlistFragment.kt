package com.jaguiler.pocketcoin.ui.watchlist

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaguiler.pocketcoin.R
import com.jaguiler.pocketcoin.databinding.WatchlistFragmentBinding
import com.jaguiler.pocketcoin.ui.API.Coin

private const val TAG = "WatchlistFragment"

class WatchlistFragment : Fragment() {

    private var binding: WatchlistFragmentBinding? = null
    val viewModel: WatchlistViewModel by activityViewModels()
    private val watchlistAdapter = WatchlistAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val watchlistBinding = WatchlistFragmentBinding.inflate(inflater, container, false)
        binding = watchlistBinding
        return watchlistBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            fetchCoins()

            watchlistRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = watchlistAdapter
            }
        }

        viewModel.coinList.observe(viewLifecycleOwner, {
            watchlistAdapter.updateWatchlist(viewModel.coinMap)
        })
    }

    private fun fetchCoins() {
        for (id in viewModel.getIDs()!!) {
            Log.d(TAG, "Attempting fetch: $id")
            viewModel.getCoin(id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private inner class WatchlistAdapter : RecyclerView.Adapter<WatchlistHolder>() {

        var watchlistCoins = mutableListOf<Coin>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistHolder {
            val inflater = LayoutInflater.from(parent.context).inflate(R.layout.coin_list_item, parent, false)
            return WatchlistHolder(inflater)
        }

        override fun onBindViewHolder(holder: WatchlistHolder, position: Int) {
            holder.bind(watchlistCoins[position])
        }

        override fun getItemCount() = watchlistCoins.size

        fun updateWatchlist(coins: HashMap<Int, Coin>) {
            if(!coins.values.isEmpty()) {
                this.watchlistCoins = coins.values.toList() as MutableList<Coin>
            } else {
                this.watchlistCoins = mutableListOf()
            }
            notifyDataSetChanged()
        }

    }

    private inner class WatchlistHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var coin: Coin
        val coinNameTextView: TextView = itemView.findViewById(R.id.list_coinname_textView)
        val coinPriceTextView: TextView = itemView.findViewById(R.id.list_priceusd_value_textView)
        val coinDayVolumeTextView: TextView = itemView.findViewById(R.id.list_day_volume_val_textView)
        val coinHourChangeTextView: TextView = itemView.findViewById(R.id.list_hourchange_val_textView)
        val coinDayChangeTextView: TextView = itemView.findViewById(R.id.list_daychange_val_textView)
        val coinWeekChangeTextView: TextView = itemView.findViewById(R.id.list_weekchange_val_textView)
        val removeCoinButton: ImageButton = itemView.findViewById(R.id.list_removefromlist_imageButton)
        val coinIconImageView: ImageView = itemView.findViewById(R.id.list_coinicon_imageView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            viewModel.getClickedCoin(coin.id)
            findNavController().navigate(R.id.action_navigation_watchlist_to_navigation_coindetail)
        }

        fun bind(coin: Coin) {
            this.coin = coin
            coinNameTextView.text = coin.name
            coinPriceTextView.text = coin.price_usd.toString()
            coinDayVolumeTextView.text = coin.volume24.toPlainString()

            val hColor = if (coin.percent_change_1h < 0.0) Color.RED else Color.GREEN
            val dColor = if (coin.percent_change_24h < 0.0) Color.RED else Color.GREEN
            val wColor = if (coin.percent_change_7d < 0.0) Color.RED else Color.GREEN

            coinHourChangeTextView.text = coin.percent_change_1h.toString()
            coinDayChangeTextView.text = coin.percent_change_24h.toString()
            coinWeekChangeTextView.text = coin.percent_change_7d.toString()

            coinHourChangeTextView.setTextColor(hColor)
            coinDayChangeTextView.setTextColor(dColor)
            coinWeekChangeTextView.setTextColor(wColor)

            removeCoinButton.setOnClickListener {
                viewModel.removeCoin(coin.id)
                watchlistAdapter.updateWatchlist(viewModel.coinMap)
            }

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
            coinIconImageView.setImageResource(icon)
        }
    }
}