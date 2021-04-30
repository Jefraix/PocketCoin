package com.jaguiler.pocketcoin.ui.allcoins

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaguiler.pocketcoin.R

class CoinsFragment : Fragment() {

    companion object {
        fun newInstance() = CoinsFragment()
    }

    private lateinit var viewModel: CoinsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.coins_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoinsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}