package com.example.ltechtesttask.data.repository

import com.example.ltechtesttask.data.api.DevExamApi
import com.example.ltechtesttask.domain.models.Login
import com.example.ltechtesttask.domain.models.Mask
import com.example.ltechtesttask.domain.models.Result
import com.example.ltechtesttask.domain.repository.LoginRepository
import io.reactivex.rxjava3.core.Single

class LoginRepositoryImpl(private val service: DevExamApi): LoginRepository {

    override fun getMask(): Single<Mask> {
        return service.getPhoneMask()
    }

    override fun getResultAuthorization(phone: String, password: String): Single<Result> {
        return service.login(Login(phone, password))
    }
}