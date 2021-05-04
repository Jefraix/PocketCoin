package com.jaguiler.pocketcoin.ui.watchlist

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

    private inner class WatchlistHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinNameTextView: TextView = itemView.findViewById(R.id.list_coinname_textView)
        val coinPriceTextView: TextView = itemView.findViewById(R.id.list_priceusd_value_textView)
        val coinDayVolumeTextView: TextView = itemView.findViewById(R.id.list_day_volume_val_textView)
        val coinHourChangeTextView: TextView = itemView.findViewById(R.id.list_hourchange_val_textView)
        val coinDayChangeTextView: TextView = itemView.findViewById(R.id.list_daychange_val_textView)
        val coinWeekChangeTextView: TextView = itemView.findViewById(R.id.list_weekchange_val_textView)
        val removeCoinButton: ImageButton = itemView.findViewById(R.id.list_removefromlist_imageButton)
        val coinIconImageView: ImageView = itemView.findViewById(R.id.list_coinicon_imageView)

        fun bind(coin: Coin) {
            coinNameTextView.text = coin.name
            coinPriceTextView.text = coin.price_usd.toString()
            coinDayVolumeTextView.text = coin.volume24.toPlainString()
            coinHourChangeTextView.text = coin.percent_change_1h.toString()
            coinDayChangeTextView.text = coin.percent_change_24h.toString()
            coinWeekChangeTextView.text = coin.percent_change_7d.toString()

            removeCoinButton.setOnClickListener {
                viewModel.removeCoin(coin.id)
                watchlistAdapter.updateWatchlist(viewModel.coinMap)
            }

            val icon = when(coin.id) {
                    90 -> R.drawable.bitcoin_sym
                    else -> R.drawable.ic_outline_info_24
            }
            coinIconImageView.setImageResource(icon)
        }
    }
}