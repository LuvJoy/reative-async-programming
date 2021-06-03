package ReactiveX.Observable

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import java.util.concurrent.TimeUnit

fun main() {
    // [방법1] Create를 사용한 생성
    val observableA: Observable<Int> = Observable.create {
        it.onNext(100)
        it.onNext(100)
        it.onComplete()
    }

    observableA.subscribeBy(
        onNext = { println("onNext $it") },
        onError = { println("onError ${it.message}") },
        onComplete = { println("onComplete") }
    )

    // [방법2] Iterator(컬렉션 타입들)에 뒤에 toObservable을 붙여 생성
    val observableB: Observable<Int> = arrayListOf<Int>(1, 2, 3, 4, 5).toObservable()

    observableB.subscribeBy(
        onNext = { println("onNext $it") },
        onError = { println("onError ${it.message}") },
        onComplete = { println("onComplete") }
    )

    // [방법3] Defer를 사용한 지연 초기화 (구독할때 생성됨)
    val observableC: Observable<Int> = Observable.defer {
        arrayListOf<Int>(1,2,3,4,5).toObservable()
    }

    observableC.subscribeBy(
        onNext = { println("onNext $it") },
        onError = { println("onError ${it.message}") },
        onComplete = { println("onComplete") }
    )

    // [방법4] Just를 사용해 생성 (특정 파라미터로 아이템을 받아서 옵져버블로 발행)
    val observableD: Observable<Int> = Observable.just(1, 2, 3, 4, 5)

    observableD.subscribeBy(
        onNext = { println("onNext $it") },
        onError = { println("onError ${it.message}") },
        onComplete = { println("onComplete") }
    )

    // [방법4] interval 사용해 생성 (특정 시간별로 연속된 정수형을 배)
    val observableE: Observable<Long> = Observable.interval(2, TimeUnit.SECONDS)
        .take(10) // 4초 간격으로 5번 발행하겠다

    observableE.subscribeBy(
        onNext = { println("onNext $it") },
        onError = { println("onError ${it.message}") },
        onComplete = { println("onComplete") }
    )
}