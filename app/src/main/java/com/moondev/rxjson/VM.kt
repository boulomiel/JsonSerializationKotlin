package com.moondev.rxjson

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

class VM  : ViewModel(){

    var listOfRepo =  MutableLiveData<List<Repo>>()
    private val disposable =  CompositeDisposable()


    /**
     * Asynchronous work to prevent us from blocking the UI while fetching and transforming the data.
     * Here we update the MutableLiveData listOfRepo
     *
     * For learning and debugging purpose we use the fancy method subscribeBy()
     */

    fun fetchListOfData(){
        val observable = getStringObservable()
        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {result ->listOfRepo.value =  result },
                onError = {error-> Log.e("VM", "fetchListOfData : ${error.localizedMessage}")}
            ).addTo(disposable)
    }

    /**
     * Returns an Observable like below :
     * URl => String => List<Repo> wrapped into an Observable
     */

    private fun getStringObservable() : Observable<List<Repo>> {
        return Observable.create { emitter ->
            emitter.onNext(
                Json {ignoreUnknownKeys = true  }.decodeFromString(URL(MainActivity.URL).readText())
            )
        }
    }

    /**
     * When the view model is destroyed we get rid of all observables
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}