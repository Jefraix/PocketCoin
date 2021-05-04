package com.jaguiler.pocketcoin.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jaguiler.pocketcoin.R
import com.jaguiler.pocketcoin.databinding.InfoFragmentBinding
import com.jaguiler.pocketcoin.BuildConfig


class InfoFragment : Fragment() {

    private var binding: InfoFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val bindingInfo = InfoFragmentBinding.inflate(inflater, container, false)
        binding = bindingInfo

        binding?.apply {
            appNameTextView.text = resources.getString(R.string.app_name)
            appVersionTextView.text = BuildConfig.VERSION_NAME
        }

        return bindingInfo.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}