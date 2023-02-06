package com.example.ltechtesttask.data.api

import com.example.ltechtesttask.domain.models.Element
import com.example.ltechtesttask.domain.models.Login
import com.example.ltechtesttask.domain.models.Mask
import com.example.ltechtesttask.domain.models.Result
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface DevExamApi {

    @GET("v1/phone_masks")
    fun getPhoneMask(): Single<Mask>

    @POST("v1/auth")
    fun login(@Body request: Login): Single<Result>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("v1/posts")
    fun getElements(): Single<List<Element>>
}