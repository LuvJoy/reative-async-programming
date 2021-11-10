package ReactiveX.Observable

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.lang.NumberFormatException
import java.util.concurrent.TimeUnit

fun main() {
    testDoOnEach()
}

fun testOnError() {
    val observable = Observable.interval(1, TimeUnit.SECONDS)
        .subscribeBy(
            onNext = {
                if(it == 5L) { throw RuntimeException("I hate 5") }
                println("$it")
                Thread.sleep(1000L)
            },
            onError = { println("[error] ${it.message}") },
            onComplete = { println("[complete]") }
        )

    Thread.sleep(10000L)
}

fun testOnErrorReturn() {
    Observable.just("1", "2", "3", "b", "c")
        .map { it.toInt() }
        .onErrorReturn { return@onErrorReturn -1 }
        .subscribe { println(it) }
}

fun testOnErrorResumeNext() {
    Observable.just("1", "2", "c", "d")
        .map { it.toInt() }
        .onErrorResumeNext { Observable.just(3, 4, 5, 6) }
        .subscribe { println(it) }
}

fun testRetry() {
    Observable.just("1", "2", "a", "b", "c")
        .map { it.toInt() }
        .retry(3)
        .onErrorResumeNext { Observable.just(101) }
        .subscribe { println(it) }
}

fun testDoOnEach() {
    Observable.just(1, 2,3, 4, 5)
        .doOnEach { notification ->
            println("     [Value] - ${notification.value}")
            println("    [onNext] - ${notification.isOnNext}")
            println("   [onError] - ${notification.isOnError}")
            println("[onComplete] - ${notification.isOnComplete}")
        }
        .subscribe { println(it) }
}