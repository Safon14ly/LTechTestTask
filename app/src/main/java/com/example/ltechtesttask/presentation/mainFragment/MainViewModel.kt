package com.example.ltechtesttask.presentation.mainFragment

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ltechtesttask.presentation.MainActivity
import com.example.ltechtesttask.R
import com.example.ltechtesttask.domain.models.Element
import com.example.ltechtesttask.domain.useCases.ElementsUseCase
import com.example.ltechtesttask.presentation.app.AppConstants
import com.example.ltechtesttask.presentation.core.BaseViewModel
import org.koin.java.KoinJavaComponent.getKoin

class MainViewModel(private val elementsUseCase: ElementsUseCase): BaseViewModel() {

    private val elements: MutableLiveData<List<Element>> by lazy { MutableLiveData() }
    val elementsData: LiveData<List<Element>> by lazy { elements }

    init {
        getElements()
    }

    // Получение элеметов для отображения в RV
    fun getElements() {
        compositeDisposable.add(
            elementsUseCase.getElements()
                .subscribe({ container ->
                    elements.value = container
                }, {
                    it.message?.let { message ->
                        showToast(message) }
                })
        )
    }

    // Нажатие на элемент RV с передачей его на следующий экран
    fun onItemClick(element: Element) {
        val bundle = Bundle()
        bundle.putParcelable(AppConstants.ELEMENT, element)

        getKoin().getProperty<MainActivity>("MainActivity")
            ?.navController?.navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle
            )
    }
}