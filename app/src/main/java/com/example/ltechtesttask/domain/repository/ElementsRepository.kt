package com.example.ltechtesttask.domain.repository

import com.example.ltechtesttask.domain.models.*
import io.reactivex.rxjava3.core.Single

interface ElementsRepository {
    fun getElements(): Single<List<Element>>
}