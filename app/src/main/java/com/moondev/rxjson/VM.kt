package com.moondev.rxjson

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

class VM  : ViewModel(){

    var listOfRepo =  MutableLiveData<List<Repo>>()
    private val disposable =  CompositeDisposable()



    fun fetchListOfData(){
        val observable = getStringObservable()
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                    result ->
                listOfRepo.value =  result

            }.addTo(disposable)
    }

    private fun getStringObservable() : Observable<List<Repo>> {
        return Observable.create { emitter ->
            emitter.onNext(
                Json {ignoreUnknownKeys = true  }.decodeFromString(URL(MainActivity.URL).readText())
            )
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}