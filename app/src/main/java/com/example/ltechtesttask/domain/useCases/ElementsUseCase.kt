package com.example.ltechtesttask.domain.useCases

import com.example.ltechtesttask.domain.models.Element
import com.example.ltechtesttask.domain.repository.ElementsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ElementsUseCase(private val elementsRepository: ElementsRepository) {

    fun getElements(): Single<List<Element>> {
        return elementsRepository.getElements()
            .map { it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}