package com.jaguiler.pocketcoin.ui.allcoins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoinsViewModel : ViewModel() {

    private val _url = MutableLiveData<String>().apply {
        value = "about:blank"
    }
    val url: LiveData<String> = _url

    fun setUrl(url: String) {
        _url.value = url
    }
}