package com.xnfl16.elaundrie.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.source.network.State
import com.xnfl16.elaundrie.utils.Constant
import com.xnfl16.elaundrie.utils.LoadingDialog
import com.xnfl16.elaundrie.utils.NetworkConnectivity


class MainActivity : AppCompatActivity() {
    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        showNotification()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        val toolbar = supportActionBar
        val appBarConfig = AppBarConfiguration.Builder(R.id.mainFragment).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashScreenFragment -> toolbar?.hide()
                else -> toolbar?.show()
            }
        }
        checkNetworkConnectivity()
    }
    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val desc = getString(R.string.channel_desc)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constant.CHANNEL_ID, name, importance).apply {
                description = desc
                enableVibration(true)
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun checkNetworkConnectivity() {
        loadingDialog.start(State.LOADING)
        val networkConnection = NetworkConnectivity(this.applicationContext)
        networkConnection.observe(this) { isConnected ->
            if (loadingDialog.progressDialog.isShowing) {
                loadingDialog.dismiss()
            }
            if (isConnected) {
                loadingDialog.dismiss()

            } else {
                Toast.makeText(
                    this,
                    this.getString(R.string.tidak_ada_koneksi_internet),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}