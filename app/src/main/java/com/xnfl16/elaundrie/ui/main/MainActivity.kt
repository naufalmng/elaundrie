    package com.xnfl16.elaundrie.ui.main

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.xnfl16.elaundrie.R
import com.xnfl16.elaundrie.core.data.network.State
import com.xnfl16.elaundrie.utils.LoadingDialog
import com.xnfl16.elaundrie.utils.NetworkConnectivity


    class MainActivity : AppCompatActivity() {
        private val loadingDialog: LoadingDialog by lazy{
            LoadingDialog(this)
        }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        val toolbar = supportActionBar
        val appBarConfig = AppBarConfiguration.Builder(R.id.mainFragment).build()
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfig)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashScreenFragment -> toolbar?.hide()
                else -> toolbar?.show()
            }
        }
        checkNetworkConnectivity()
    }

        private fun checkNetworkConnectivity() {
            loadingDialog.start(State.LOADING)
            val networkConnection = NetworkConnectivity(this.applicationContext)
            networkConnection.observe(this){isConnected ->
                if(loadingDialog.progressDialog.isShowing) {
                    loadingDialog.dismiss()
                }
                if(isConnected){
                    loadingDialog.dismiss()

                }else{
                    loadingDialog.start(State.FAILED)
                }
            }
        }

        override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}