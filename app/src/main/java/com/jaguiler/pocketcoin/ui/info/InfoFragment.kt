package com.jaguiler.pocketcoin.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jaguiler.pocketcoin.R
import com.jaguiler.pocketcoin.databinding.InfoFragmentBinding

class InfoFragment : Fragment() {

    private lateinit var viewModel: InfoViewModel
    private var binding: InfoFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bindingInfo = InfoFragmentBinding.inflate(inflater, container, false)
        binding = bindingInfo

        binding?.apply {
            returnImageButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_info_to_navigation_watchlist)
            }
        }
        return bindingInfo.root
    }

}