package com.example.ltechtesttask.api

import com.example.ltechtesttask.api.models.Element
import com.example.ltechtesttask.api.models.Login
import com.example.ltechtesttask.api.models.Mask
import com.example.ltechtesttask.api.models.Result
import retrofit2.Call
import retrofit2.http.*

interface DevExamService {

    @GET("v1/phone_masks")
    fun getPhoneMask(): Call<Mask>

    @POST("v1/auth")
    fun login(@Body request: Login): Call<Result>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("v1/posts")
    fun getElements(): Call<List<Element>>
}