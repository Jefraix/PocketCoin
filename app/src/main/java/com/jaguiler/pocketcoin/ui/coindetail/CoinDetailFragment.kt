package com.jaguiler.pocketcoin.ui.coindetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaguiler.pocketcoin.R

class CoinDetailFragment : Fragment() {

    companion object {
        fun newInstance() = CoinDetailFragment()
    }

    private lateinit var viewModel: CoinDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.coin_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoinDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}