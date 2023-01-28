package com.example.ltechtesttask.viewModels

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ltechtesttask.App
import com.example.ltechtesttask.MainActivity
import com.example.ltechtesttask.R
import com.example.ltechtesttask.api.Api
import com.example.ltechtesttask.api.models.Element
import com.example.ltechtesttask.ui.detailsFragment.DetailsFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.getKoin
import java.util.concurrent.TimeUnit

class MainViewModel: ViewModel(), IViewModel {

    private var elements: MutableLiveData<List<Element>> = MutableLiveData()

    fun getElementsContainer() = elements

    init {
        getElements()
    }

    // Получение элеметов для отображения в RV с задержкой в 10 секунд и последующими повторами запроса на сервер
    fun getElements() {
        getElementsObservable()
            .subscribeOn(Schedulers.io())
            .delay(10, TimeUnit.SECONDS)
            .repeat()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ container ->
                elements.postValue(container)
            }, {
                it.message?.let { message ->
                    showToast(message) }
            }, {
                showToast(R.string.onErrorLogin)
            })
    }

    // Используем Observable
    private fun getElementsObservable(): Observable<List<Element>> {
        return Observable.create {
            Api.instance.getElements().execute().body()?.let { elements ->
                it.onNext(elements)
            }
            it.onComplete()
        }
    }

    // Клик по элементу RV с передачей его на следующий экран
    fun onItemClick(element: Element) {
        val bundle = Bundle()
        bundle.putParcelable(DetailsFragment.ELEMENT, element)

        getKoin().getProperty<MainActivity>("MainActivity")
            ?.navController?.navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle
            )
    }

    override fun showToast(message: String) {
        Toast.makeText(App.instance.applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showToast(message: Int) {
        Toast.makeText(App.instance.applicationContext, message, Toast.LENGTH_LONG).show()
    }
}