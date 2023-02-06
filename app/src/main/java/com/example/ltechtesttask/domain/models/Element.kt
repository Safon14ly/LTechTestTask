package com.example.ltechtesttask.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Element(
    val id: String,
    val title: String? = "",
    val text: String? = "",
    val image: String? = "",
    val sort: Int = 0,
    val date: String? = ""
): Parcelable
