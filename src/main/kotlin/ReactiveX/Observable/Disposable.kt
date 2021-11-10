package ReactiveX.Observable

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.Exception

fun main() {
    testCompositeDisposable()
}

fun generateDisposable() {
    val observable: Observable<Int> = Observable.just(1, 2, 3, 4)
    val disposable: Disposable = observable.subscribe { println(it) }
}

fun testDisposable() {
    val disposable = Observable.interval(1, TimeUnit.SECONDS)
        .subscribe { println(it) }

    Thread {
        try {
            Thread.sleep(3000L)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        disposable.dispose()
    }.start()
}

fun testCompositeDisposable() {
    val disposable1 = Observable.interval(1, TimeUnit.SECONDS)
        .subscribe { println("Disposable1 - $it") }
    val disposable2 = Observable.interval(1, TimeUnit.SECONDS)
        .subscribe { println("Disposable2 - $it") }
    val disposable3 = Observable.interval(1, TimeUnit.SECONDS)
        .subscribe { println("Disposable3 - $it") }

    val compositeDisposable = CompositeDisposable()
    compositeDisposable.addAll(disposable1, disposable2, disposable3)

    Thread {
        try {
            Thread.sleep(3000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        compositeDisposable.dispose()
    }.start()
}

fun cancelOneObservable() {
    val disposable = Observable.create<Int> { emitter ->
        Thread {
            try {
                repeat(100) { number ->
                    println("repeat " + Thread.currentThread().name)
                    emitter.onNext(number)
                    Thread.sleep(1000L)
                }
            } catch (e: Exception) {
                Thread.currentThread().interrupt()
            }
        }.start()
    }.subscribe {
        println("onNext " + Thread.currentThread().name)
        println("$it")
    }

    Thread.sleep(3000)
    disposable.dispose()
    println(disposable.isDisposed)
}