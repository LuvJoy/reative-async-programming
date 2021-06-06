package ReactiveX.Observable

import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject

fun main() {
    //testBehaviorSubject()
    //testAsyncSubject()
    //testPublishSubject()
    testReplaySubject()
}

fun testBehaviorSubject() {
    val behaviorSubject = BehaviorSubject.create<Int>()
    behaviorSubject.onNext(4)
    behaviorSubject.onNext(6)

    val disposable = behaviorSubject.subscribeBy(
        onNext = {
            println(it)
        }
    )
    behaviorSubject.onNext(8)
    disposable.dispose()

    behaviorSubject.onNext(10)
}

fun testAsyncSubject() {
    val asyncSubject = AsyncSubject.create<Int>()
    asyncSubject.subscribeBy(
        onNext = {
            println("#1 Observer : $it")
        }
    )
    asyncSubject.onNext(1)

    asyncSubject.subscribeBy {
        println("#2 Observer : $it")
    }
    asyncSubject.onNext(2)
    asyncSubject.onNext(3)
    asyncSubject.onComplete()
}

fun testPublishSubject() {
    val publishSubject = PublishSubject.create<Int>()
    with(publishSubject) {
        onNext(1)
        subscribe {
            println("#1 Observer : $it")
        }
        onNext(2)
        onNext(3)
        subscribe {
            println("#2 Observer : $it")
        }
        onNext(4)
        onNext(5)
    }
}

fun testReplaySubject() {
    val replaySubject = ReplaySubject.create<Int>()
    with(replaySubject) {
        onNext(1)
        onNext(2)
        onNext(3)

        subscribe {
            println("#1 Observer : $it")
        }
    }
}