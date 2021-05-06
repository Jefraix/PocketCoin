package com.jaguiler.pocketcoin.ui.allcoins

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaguiler.pocketcoin.R
import com.jaguiler.pocketcoin.databinding.CoinsFragmentBinding
import com.jaguiler.pocketcoin.ui.api.Coin
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
            allcoinsProgressBar.visibility = View.VISIBLE
            retrievingCoinsTextView.visibility = View.VISIBLE

            viewModel.getCoinRange(0, 20)

            allcoinsReyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = allCoinsAdapter
            }
        }

        viewModel.biggerCoinList.observe(viewLifecycleOwner, {
            allCoinsAdapter.updateCoinslist(viewModel.get20CoinList()!!)

            binding?.allcoinsProgressBar?.visibility = View.GONE
            binding?.retrievingCoinsTextView?.visibility = View.GONE
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

    private inner class AllCoinsHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var coin: Coin
        val coinNameTextView: TextView = itemView.findViewById(R.id.coinname_textView)
        val coinPriceTextView: TextView = itemView.findViewById(R.id.priceusd_value_textView)
        val coinHourChangeTextView: TextView = itemView.findViewById(R.id.hourchange_val_textView)
        val coinDayChangeTextView: TextView = itemView.findViewById(R.id.daychange_val_textView)
        val coinWeekChangeTextView: TextView = itemView.findViewById(R.id.weekchange_val_textView)
        val addCoinButton: ImageButton = itemView.findViewById(R.id.addtolist_imageButton)
        val coinIconImageView: ImageView = itemView.findViewById(R.id.coinicon_imageView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            viewModel.getClickedCoin(coin.id)
            findNavController().navigate(R.id.action_navigation_allcoins_to_navigation_coindetail)
        }

        fun bind(coin: Coin) {
            this.coin = coin
            coinNameTextView.text = coin.name

            val price = "$" + String.format("%,.2f", coin.price_usd)
            coinPriceTextView.text = price

            val hColor = if (coin.percent_change_1h < 0.0) Color.RED else Color.GREEN
            val dColor = if (coin.percent_change_24h < 0.0) Color.RED else Color.GREEN
            val wColor = if (coin.percent_change_7d < 0.0) Color.RED else Color.GREEN

            coinHourChangeTextView.text = coin.percent_change_1h.toString()
            coinDayChangeTextView.text = coin.percent_change_24h.toString()
            coinWeekChangeTextView.text = coin.percent_change_7d.toString()

            coinHourChangeTextView.setTextColor(hColor)
            coinDayChangeTextView.setTextColor(dColor)
            coinWeekChangeTextView.setTextColor(wColor)

            addCoinButton.isVisible = true
            addCoinButton.setOnClickListener {
                viewModel.addCoin(coin.id)
                AllCoinsAdapter().updateCoinslist(viewModel.get20CoinList()!!)
                context?.toast(resources.getString(R.string.addsuccess_dialog, coin.name))
                addCoinButton.isVisible = false
            }

            if(viewModel.coinMap.containsKey(coin.id)){
                addCoinButton.isVisible = false
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
                33433 -> R.drawable.wrapped_bitcoin_sym
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

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message , duration).show()
}