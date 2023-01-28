package com.example.ltechtesttask.api.models

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("success")
    val isResult: Boolean = false
)