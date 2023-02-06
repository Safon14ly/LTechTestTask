package com.example.ltechtesttask.domain.models

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("success")
    val isResult: Boolean = false
)