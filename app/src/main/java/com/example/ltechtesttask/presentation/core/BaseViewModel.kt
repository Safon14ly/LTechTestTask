package com.example.ltechtesttask.presentation.core

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.ltechtesttask.presentation.app.App
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun showToast(message: String) {
        Toast.makeText(App.instance.applicationContext, message, Toast.LENGTH_LONG).show()
    }

    protected fun showToast(message: Int) {
        Toast.makeText(App.instance.applicationContext, message, Toast.LENGTH_LONG).show()
    }
}