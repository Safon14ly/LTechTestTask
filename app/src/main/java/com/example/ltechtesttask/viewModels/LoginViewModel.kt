package com.example.ltechtesttask.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ltechtesttask.App
import com.example.ltechtesttask.Empty
import com.example.ltechtesttask.MainActivity
import com.example.ltechtesttask.R
import com.example.ltechtesttask.api.Api
import com.example.ltechtesttask.api.models.Mask
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.getKoin


class LoginViewModel: ViewModel(), IViewModel {

    private val sharedPreferences = App.instance.getSharedPreferences(PREFERENCES_PATH, Context.MODE_PRIVATE)

    private var phoneMask: MutableLiveData<Mask> = MutableLiveData()

    fun getPhoneMask() = phoneMask

    init {
        getMask()
    }

    override fun showToast(message: String) {
        Toast.makeText(App.instance.applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showToast(message: Int) {
        Toast.makeText(App.instance.applicationContext, message, Toast.LENGTH_LONG).show()
    }

    // Клик по кнопке авторизации
    fun onLoginClick(phone: String, password: String) {
        getResultObservable(phone, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isResult ->
                if (isResult) {
                    savePhone(phone)
                    savePassword(password)
                    getKoin().getProperty<MainActivity>("MainActivity")
                        ?.navController?.navigate(R.id.action_loginFragment_to_mainFragment)
                    showToast(App.instance.getString(R.string.onCompleteLogin))
                }
            }, {
                it.message?.let { message ->
                    showToast(message)
                }
            })
    }

    // Получение сохраненного номера телефона
    fun obtainPhone(): String {
        return getString(PHONE)
    }

    // Получение сохраненного пароля
    fun obtainPassword(): String {
        return getString(PASSWORD)
    }

    // Сохранение номера телефона
    private fun savePhone(phone: String) {
        putString(PHONE, phone)
    }

    // Сохранение пароля
    private fun savePassword(password: String) {
        putString(PASSWORD, password)
    }

    // Сохранение данных в виде пар ключ-значение
    private fun putString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
                .apply()
        }
    }

    // Получение данных по ключу
    private fun getString(key: String): String {
        sharedPreferences.getString(key, "")?.let {
            return it
        }
        return String.Empty
    }

    // Получение маски номера телефона
    private fun getMask() {
        getMaskObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ maskContainer ->
                phoneMask.postValue(maskContainer)
            }, {
                it.message?.let { message ->
                    showToast(message)
                }
            }, {
                showToast(App.instance.getString(R.string.onCompleteMask))
            })
    }


    // Используем Observable
    private fun getMaskObservable(): Observable<Mask> {
        return Observable.create {
            Api.instance.getPhoneMask().execute().body()?.let { mask ->
                it.onNext(mask)
            }
            it.onComplete()
        }
    }

    // Используем Observable
    private fun getResultObservable(phone: String, password: String): Observable<Boolean> {
        return Observable.create {
            Api.instance.login(phone, password).execute().body()?.let { result ->
                it.onNext(result.isResult)
            }
            it.onComplete()
        }
    }

    companion object {
        private const val PREFERENCES_PATH = "preferencesPath"
        private const val PHONE = "phone"
        private const val PASSWORD = "password"
    }
}