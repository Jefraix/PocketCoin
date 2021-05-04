package com.jaguiler.pocketcoin.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaguiler.pocketcoin.R
import com.jaguiler.pocketcoin.databinding.WatchlistFragmentBinding
import com.jaguiler.pocketcoin.ui.API.Coin

class WatchlistFragment : Fragment() {

    private var binding: WatchlistFragmentBinding? = null
    private lateinit var viewModel: WatchlistViewModel
    private val watchlistAdapter = WatchlistAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val watchlistBinding = WatchlistFragmentBinding.inflate(inflater, container, false)
        binding = watchlistBinding

        binding?.apply {
            watchlistRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = watchlistAdapter
            }
        }

        return watchlistBinding.root
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

    }

    private inner class WatchlistHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinNameTextView: TextView = itemView.findViewById(R.id.list_coinname_textView)
        val coinPriceTextView: TextView = itemView.findViewById(R.id.list_priceusd_value_textView)
        val coinHourChangeTextView: TextView = itemView.findViewById(R.id.list_hourchange_val_textView)
        val coinDayChangeTextView: TextView = itemView.findViewById(R.id.list_daychange_val_textView)
        val coinWeekChangeTextView: TextView = itemView.findViewById(R.id.list_weekchange_val_textView)

        fun bind(coin: Coin) {
            coinNameTextView.text = coin.name
            coinPriceTextView.text = coin.price_usd.toString()
            coinHourChangeTextView.text = coin.percent_change_1h.toString()
            coinDayChangeTextView.text = coin.percent_change_24h.toString()
            coinWeekChangeTextView.text = coin.percent_change_7d.toString()
        }
    }
}