package ReactiveX.Observable

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun main() {
}

/* Defer */
fun testDeferObservable() {

    val justObservable = Observable.just(getCurrentTime())

    val deferObservable = Observable.defer {
        Observable.just(getCurrentTime())
    }

    println("현재시간(subscribe X) : ${getCurrentTime()}")

    Thread.sleep(5000L)

    println("현재시간(subscribe O) : ${getCurrentTime()}")

    justObservable.subscribe { time ->
        println("Just Observable : $time")
    }

    deferObservable.subscribe { time ->
        println("Defer Observable : $time")
    }

}

private fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA)
    return sdf.format(System.currentTimeMillis())
}

/* Empty, Never */
fun testEmptyAndNeverObservable() {

    val neverObservable = Observable.never<Unit>().doOnComplete {
        println("neverObservable 완료")
    }
    val emptyObservable = Observable.empty<Unit>().doOnComplete {
        println("emptyObservable 완료")
    }

    neverObservable.subscribe()
    emptyObservable.subscribe()
}

/* interval */
fun testIntervalObservable() {
    val intervalObservable = Observable.interval(1, TimeUnit.SECONDS)
        .take(5)

    intervalObservable.subscribeBy(
        onNext = ::println,
        onComplete = { println("Complete") },
        onError = { println("Error") }
    )

    Thread.sleep(5000L)
}

/* Timer */
fun testTimerObservable() {
    val timerObservable = Observable.timer(5, TimeUnit.SECONDS)

    timerObservable.subscribeBy(
        onNext = ::println,
        onComplete = { println("Ccomplete") },
        onError = { println("Error") }
    )

    Thread.sleep(5500L)
}

/* map */
fun testMapOperator() {
    val namesObservable = Observable.just("John", "James", "Joseph", "Kelly")
        .map {
            it + "님"
        }

    namesObservable.subscribe { println(it) }


    val nameLengthObservable = Observable.just("John", "James", "Joseph", "Kelly")
        .map {
            it.length
        }

    nameLengthObservable.subscribe { println(it) }
}

/* flatMap */
fun testFlatMapOperator() {
    val typeObservable = Observable.just("185cm", "180cm", "175cm", "170cm")
        .flatMap { height ->
            Observable.just("$height 남", "$height 여")
        }

    typeObservable.subscribe { println(it) }
}

/* buffer */
fun testBuffer() {
    val rangeObservable = Observable.range(1, 10)
        .buffer(3)
        .subscribeBy(
            onNext = {
                println("onNext() - execute")
                print("$it ")
                println()
            }
        )
}

/* scan */
fun testScan() {
    val observable = Observable.just("오", "징", "어", "게", "임")
        .scan { t1, t2 -> t1 + t2 }
        .subscribe {
            println(it)
        }
}