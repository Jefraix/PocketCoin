package com.jaguiler.pocketcoin

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PocketCoin)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_watchlist,
                R.id.navigation_allcoins,
                R.id.navigation_settings,
                R.id.navigation_info))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.navigation_watchlist, R.id.navigation_allcoins
                    -> {
                        supportActionBar?.show()
                        navView.visibility = View.VISIBLE
                    }
                R.id.navigation_settings, R.id.navigation_info, R.id.navigation_coindetail
                    -> {
                        supportActionBar?.hide()
                        navView.visibility = View.INVISIBLE
                    }
            }

            supportActionBar?.let {
                it.title = when (destination.id) {
                    R.id.navigation_settings -> getString(R.string.settings_title)
                    R.id.navigation_watchlist -> getString(R.string.watchlist_title)
                    R.id.navigation_allcoins -> getString(R.string.allcoins_title)
                    R.id.navigation_info -> getString(R.string.info_title)
                    else -> getString(R.string.app_name)
                }
            }
        }

        if (!isConnected())
            Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show()
    }

    @Suppress("DEPRECATION")
    @SuppressLint("ObsoleteSdkInt")
    private fun isConnected(): Boolean {

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = cm.activeNetwork ?: return false
            val nwCapabilities = cm.getNetworkCapabilities(nw) ?: return false
            return when {
                nwCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                nwCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = cm.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}