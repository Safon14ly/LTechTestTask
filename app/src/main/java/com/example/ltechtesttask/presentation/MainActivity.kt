package com.example.ltechtesttask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ltechtesttask.R
import org.koin.android.ext.android.getKoin

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация навигации
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.vNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment,
                R.id.mainFragment,
                R.id.detailsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        getKoin().setProperty("MainActivity", this)
    }
}