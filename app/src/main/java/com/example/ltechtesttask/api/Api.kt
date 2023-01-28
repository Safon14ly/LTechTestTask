package com.example.ltechtesttask.api

import com.example.ltechtesttask.api.models.Element
import com.example.ltechtesttask.api.models.Login
import com.example.ltechtesttask.api.models.Mask
import com.example.ltechtesttask.api.models.Result
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Api {

    companion object {
        val instance = Api()

        private const val BASE_URL = "http://dev-exam.l-tech.ru/api/"
    }

    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: DevExamService = retrofitBuilder.create()

    //===============================

    // Получение маски для ввода номера телефона
    fun getPhoneMask(): Call<Mask> {
        return service.getPhoneMask()
    }

    //Авторизация
    fun login(phone: String, password: String): Call<Result> {
        return service.login(Login(phone, password))
    }

    // Получение элементов на главном экране
    fun getElements(): Call<List<Element>> {
        return service.getElements()
    }

    // Получение ссылки на изображение элемента
    fun getImageElement(id: String?): String {
        return "$BASE_URL$id"
    }
}