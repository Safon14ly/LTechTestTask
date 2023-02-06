package com.example.ltechtesttask.presentation.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class App: Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@App)
            AppModule.injectModules()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}