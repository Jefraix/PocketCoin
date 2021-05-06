package com.jaguiler.pocketcoin.ui.watchlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaguiler.pocketcoin.ui.api.Coin
import com.jaguiler.pocketcoin.ui.api.Coinlist
import com.jaguiler.pocketcoin.ui.api.LoreService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "WatchlistViewModel"

class WatchlistViewModel(app: Application) : AndroidViewModel(app) {

    private var _watchlistIDs = MutableLiveData<ArrayList<Int>>()
    private val watchlistIDs: LiveData<ArrayList<Int>> = _watchlistIDs

    var coinList: MutableLiveData<List<Coin>> = MutableLiveData()
    var coinMap: HashMap<Int, Coin> = HashMap()

    var biggerCoinList: MutableLiveData<Coinlist> = MutableLiveData()

    var selectedCoin: MutableLiveData<Coin> = MutableLiveData()

    init {
        setIDs(arrayListOf())
        //addCoin(90)
        //addCoin(80)
    }

    fun addCoin(id: Int) {
        _watchlistIDs.value?.add(id)
    }

    fun removeCoin(id: Int) {
        _watchlistIDs.value?.remove(id)
        coinMap.remove(id)
    }

    private fun setIDs(ids: ArrayList<Int>) {
        _watchlistIDs.value = ids
    }

    fun getIDs() = watchlistIDs.value

    fun get20CoinList() = biggerCoinList.value?.data

    fun idsToStringSet() : MutableSet<String> {
        val ids = getIDs()
        val idSet: MutableSet<String> = mutableSetOf()

        if (ids != null) {
            Log.d(TAG, "Key set size while making set: ${ids.size}")
            for(id in ids) {
                idSet.add(id.toString())
            }
        }
        Log.d(TAG, "Made String set: $idSet")

        return idSet
    }

    fun stringSetToIDs(idSet: Set<String>?) {
        val newIDs: ArrayList<Int> = arrayListOf()
        if (idSet != null) {
            for(id in idSet) {
                newIDs.add(id.toInt())
            }
        }
         setIDs(newIDs)
    }

    fun getClickedCoin(id: Int) {
        val request: Call<List<Coin>> = coinApi.getCoinById(id)

        request.enqueue(object : Callback<List<Coin>> {
            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                if(response.isSuccessful) {
                    val data = response.body()?.toList()
                    val coin = data?.get(0)
                    selectedCoin.postValue(coin)
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
                Log.d(TAG, "Unable to fetch coin data")
            }

        })
    }

    fun getCoin(id: Int) {
        val request: Call<List<Coin>> = coinApi.getCoinById(id)

        request.enqueue(object : Callback<List<Coin>> {
            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                if(response.isSuccessful) {
                    coinList.postValue(response.body())
                    val data = response.body()?.toList()
                    val coin = data?.get(0)
                    coinMap[coin?.id!!] = coin
                    Log.d(TAG, "Fetched coin: ${coin.id}")
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
                Log.d(TAG, "Unable to fetch coin data")
            }

        })
    }

    fun getCoinRange(start: Int, limit: Int) {
        val request: Call<Coinlist> = coinApi.getTop20Coins(start, limit)

        request.enqueue(object : Callback<Coinlist> {
            override fun onResponse(call: Call<Coinlist>, response: Response<Coinlist>) {
                if(response.isSuccessful) {
                    biggerCoinList.postValue(response.body())
                    //val data = response.body()?.data?.toList()
                    //if (data != null) coinArray = data
                    Log.d(TAG, "Fetched 20 coins")
                }
            }

            override fun onFailure(call: Call<Coinlist>, t: Throwable) {
                Log.d(TAG, "Unable to fetch coin data of 20 coins")
            }

        })
    }

    companion object {
        private const val BASE_URL = "https://api.coinlore.net/"

        val coinApi: LoreService by lazy {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return@lazy retrofit.create(LoreService::class.java)
        }
    }
}