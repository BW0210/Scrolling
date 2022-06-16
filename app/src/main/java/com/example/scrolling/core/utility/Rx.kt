package com.example.scrolling.core.utility

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


fun <T> Observable<T>.fromWorkerToMainWithOutAny(): Observable<T> {
    return this.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> calculatorData(
    data: ArrayList<T>,
    onNext: (T) -> Unit
) {
    Observable.fromIterable(data)
        .delaySubscription(100, TimeUnit.MILLISECONDS)
        .fromWorkerToMainWithOutAny()
        .subscribe {
            onNext(it)
        }

}
