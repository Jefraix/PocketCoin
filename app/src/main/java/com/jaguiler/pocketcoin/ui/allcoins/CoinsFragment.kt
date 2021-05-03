package com.jaguiler.pocketcoin.ui.allcoins

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaguiler.pocketcoin.R

class CoinsFragment : Fragment() {

    private val viewModel: CoinsViewModel by lazy {
        ViewModelProvider(this).get(CoinsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.coins_fragment, container, false)
    }

}