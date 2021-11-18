package ReactiveX.Observable

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

fun main() {
    testMergeCase1()
}

/* debounce */
fun testDebounce() {
    val observable = Observable.create<Int> { observable ->
        repeat(10) {
            observable.onNext(it)
            if (it % 2 == 0) Thread.sleep(1000L)
        }
    }.debounce(500L, TimeUnit.MICROSECONDS)

    observable.subscribe {
        println(it)
    }
}

/* distinct */
fun testDistinct() {
    val observable = Observable.just("A", "B", "C", "D", "A", "B", "C")
        .distinct()
        .subscribe { println(it) }
}

/* sample */
fun testSample() {
    Observable.interval(1, TimeUnit.SECONDS)
        .sample(2, TimeUnit.SECONDS)
        .subscribe { println(it) }

    Thread.sleep(10000L)
}

/* skip */
fun testSkip() {
    Observable.just(1, 2, 3, 4, 5)
        .skip(2)
        .subscribe { println(it) }
}

/* take */
fun testTake() {
    Observable.just(1, 2, 3, 4, 5)
        .take(2)
        .subscribe { println(it) }
}

/* all */
fun testAll() {
    Observable.just("John", "James", "Joseph", "Kim")
        .all { it.startsWith('J') }
        .subscribeBy { println("[Are Names StartWith J?] : $it") }

    Observable.just("John", "James", "Joseph", "Jim")
        .all { it.startsWith('J') }
        .subscribeBy { println("[Are Names StartWith J?] : $it") }
}

/* combineLatest */
fun testCombineLatest() {
    val numbers = Observable.create<Int> { emitter ->
        Thread {
            repeat(5) { age ->
                emitter.onNext(age)
                Thread.sleep(1000L)
            }
        }.start()
    }

    val names = Observable.create<String> { emiiter ->
        Thread {
            Thread.sleep(300L)
            emiiter.onNext("James")
            Thread.sleep(500L)
            emiiter.onNext("Jim")
            Thread.sleep(200L)
            emiiter.onNext("Joseph")
            Thread.sleep(200L)
            emiiter.onNext("Kelly")
        }.start()
    }

    Observable.combineLatest(numbers, names) { number, name -> "[$number] - $name" }
        .subscribe { println(it) }
}


/* zip */
fun testZip() {
    val numbers = Observable.create<Int> { emitter ->
        Thread {
            repeat(5) { age ->
                emitter.onNext(age)
                Thread.sleep(1000L)
            }
        }.start()
    }

    val names = Observable.create<String> { emiiter ->
        Thread {
            Thread.sleep(300L)
            emiiter.onNext("James")
            Thread.sleep(500L)
            emiiter.onNext("Jim")
            Thread.sleep(200L)
            emiiter.onNext("Joseph")
            Thread.sleep(200L)
            emiiter.onNext("Kelly")
        }.start()
    }

    Observable.zip(numbers, names) { number, name -> "[$number] - $name" }
        .subscribe { println(it) }
}

/* merge */
fun testMergeCase1() {
    val numberObservable = Observable.create<String> { emitter ->
        Thread {
            repeat(10) {
                emitter.onNext("$it")
                Thread.sleep(1000L)
            }
        }.start()
    }
    val nameObservable = Observable.fromIterable(listOf("John", "James", "Molly", "Holly", "Kimchi"))

    Observable.merge(numberObservable, nameObservable)
        .subscribeBy {
            println(it)
        }

    Observable.mergeArray(numberObservable, nameObservable)
        .subscribe {
            println(it)
        }
}

/* merge */
fun testMergeCase2() {
    val numberObservable = Observable.just(1, 2, 3, 4, 5)
    val nameObservable = Observable.fromIterable(listOf("John", "James", "Molly", "Holly", "Kimchi"))

    Observable.merge(numberObservable, nameObservable)
        .subscribe {
            println(it)
        }
}