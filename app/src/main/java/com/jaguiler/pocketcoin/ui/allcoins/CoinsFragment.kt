package com.jaguiler.pocketcoin.ui.allcoins

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaguiler.pocketcoin.R
import com.jaguiler.pocketcoin.databinding.CoinsFragmentBinding
import com.jaguiler.pocketcoin.ui.API.Coin
import com.jaguiler.pocketcoin.ui.watchlist.WatchlistViewModel

class CoinsFragment : Fragment() {

    private var binding: CoinsFragmentBinding? = null
    private val viewModel: WatchlistViewModel by activityViewModels()
    private val allCoinsAdapter = AllCoinsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val allCoinsBinding = CoinsFragmentBinding.inflate(inflater, container, false)
        binding = allCoinsBinding
        return allCoinsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {

            viewModel.getCoinRange(0, 20)

            allcoinsReyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = allCoinsAdapter
            }
        }

        viewModel.biggerCoinList.observe(viewLifecycleOwner, {
            allCoinsAdapter.updateCoinslist(viewModel.get20CoinList()!!)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private inner class AllCoinsAdapter : RecyclerView.Adapter<AllCoinsHolder>() {

        var allCoinsList = mutableListOf<Coin>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCoinsHolder {
            val inflater = LayoutInflater.from(parent.context).inflate(R.layout.coin_general_item, parent, false)
            return AllCoinsHolder(inflater)
        }

        override fun onBindViewHolder(holder: AllCoinsHolder, position: Int) {
            holder.bind(allCoinsList[position])
        }

        override fun getItemCount() = allCoinsList.size

        fun updateCoinslist(coins: List<Coin>) {
            this.allCoinsList = coins as MutableList<Coin>
            notifyDataSetChanged()
        }

    }

    private inner class AllCoinsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinNameTextView: TextView = itemView.findViewById(R.id.coinname_textView)
        val coinPriceTextView: TextView = itemView.findViewById(R.id.priceusd_value_textView)
        val coinHourChangeTextView: TextView = itemView.findViewById(R.id.hourchange_val_textView)
        val coinDayChangeTextView: TextView = itemView.findViewById(R.id.daychange_val_textView)
        val coinWeekChangeTextView: TextView = itemView.findViewById(R.id.weekchange_val_textView)
        val addCoinButton: ImageButton = itemView.findViewById(R.id.addtolist_imageButton)
        val coinIconImageView: ImageView = itemView.findViewById(R.id.coinicon_imageView)

        fun bind(coin: Coin) {
            coinNameTextView.text = coin.name
            coinPriceTextView.text = coin.price_usd.toString()
            coinHourChangeTextView.text = coin.percent_change_1h.toString()
            coinDayChangeTextView.text = coin.percent_change_24h.toString()
            coinWeekChangeTextView.text = coin.percent_change_7d.toString()

            addCoinButton.setOnClickListener {
                viewModel.addCoin(coin.id)
                AllCoinsAdapter().updateCoinslist(viewModel.get20CoinList()!!)
            }

            val icon = when(coin.id) {
                90 -> R.drawable.bitcoin_sym
                else -> R.drawable.ic_outline_info_24
            }
            coinIconImageView.setImageResource(icon)
        }
    }

}