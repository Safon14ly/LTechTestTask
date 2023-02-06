package com.example.ltechtesttask.domain.useCases

import com.example.ltechtesttask.domain.models.*
import com.example.ltechtesttask.domain.repository.LoginRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginUseCase(private val loginRepository: LoginRepository) {

    fun getMask(): Single<Mask> {
        return loginRepository.getMask()
            .map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getResultAuthorization(phone: String, password: String): Single<Result> {
        return loginRepository.getResultAuthorization(phone, password)
            .map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}