package com.example.ltechtesttask.domain.repository

import com.example.ltechtesttask.domain.models.*
import io.reactivex.rxjava3.core.Single

interface LoginRepository {
    fun getMask(): Single<Mask>
    fun getResultAuthorization(phone: String, password: String): Single<Result>
}