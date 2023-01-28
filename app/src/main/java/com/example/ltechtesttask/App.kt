package com.example.ltechtesttask

import android.app.Application
import com.example.ltechtesttask.viewModels.LoginViewModel
import com.example.ltechtesttask.viewModels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val module = module {

            viewModel {
                LoginViewModel()
            }
            viewModel {
                MainViewModel()
            }
        }

        startKoin {
            androidContext(this@App)

            modules(module)
        }
    }
}