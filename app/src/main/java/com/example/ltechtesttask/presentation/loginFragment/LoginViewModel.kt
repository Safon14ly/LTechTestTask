package com.example.ltechtesttask.presentation.loginFragment

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ltechtesttask.presentation.core.Empty
import com.example.ltechtesttask.presentation.MainActivity
import com.example.ltechtesttask.R
import com.example.ltechtesttask.domain.models.Mask
import com.example.ltechtesttask.domain.useCases.LoginUseCase
import com.example.ltechtesttask.presentation.app.App
import com.example.ltechtesttask.presentation.app.AppConstants
import com.example.ltechtesttask.presentation.core.BaseViewModel
import org.koin.java.KoinJavaComponent.getKoin


class LoginViewModel(private val loginUseCase: LoginUseCase): BaseViewModel() {

    private val sharedPreferences = App.instance.getSharedPreferences(AppConstants.PREFERENCES_PATH, Context.MODE_PRIVATE)

    private val phoneMask: MutableLiveData<Mask> by lazy { MutableLiveData() }
    val phoneMaskData: LiveData<Mask> by lazy { phoneMask }

    init {
        getMask()
    }

    // Получение маски номера телефона
    private fun getMask() {
        compositeDisposable.add(
            loginUseCase.getMask()
                .subscribe({
                    phoneMask.value = it
                    showToast(App.instance.getString(R.string.onCompleteMask))
                }, {
                    it.message?.let { message ->
                        showToast(message)
                    }
                })
        )
    }

    // Нажатие на кнопку авторизации
    fun onLoginClick(phone: String, password: String) {
        compositeDisposable.add(
            loginUseCase.getResultAuthorization(phone, password)
                .subscribe({
                    if (it.isResult) {
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
        )
    }

    // Получение сохраненного номера телефона
    fun obtainPhone(): String {
        return getString(AppConstants.PHONE)
    }

    // Получение сохраненного пароля
    fun obtainPassword(): String {
        return getString(AppConstants.PASSWORD)
    }

    // Сохранение номера телефона
    private fun savePhone(phone: String) {
        putString(AppConstants.PHONE, phone)
    }

    // Сохранение пароля
    private fun savePassword(password: String) {
        putString(AppConstants.PASSWORD, password)
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
}