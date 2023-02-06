package com.example.ltechtesttask.data.repository

import com.example.ltechtesttask.data.api.DevExamApi
import com.example.ltechtesttask.domain.models.Element
import com.example.ltechtesttask.domain.repository.ElementsRepository
import io.reactivex.rxjava3.core.Single

class ElementsRepositoryImpl(private val service: DevExamApi): ElementsRepository {

    override fun getElements(): Single<List<Element>> {
        return service.getElements()
    }
}