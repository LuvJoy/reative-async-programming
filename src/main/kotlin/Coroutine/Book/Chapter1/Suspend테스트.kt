package Coroutine.Book.Chapter1

import kotlinx.coroutines.*

fun main(): Unit = runBlocking {
    launch(Dispatchers.IO) {
        // 병렬로 2개 함수 실행
        async { suspendTask1() }
        async { suspendTask2() }
    }
}

suspend fun suspendTask1() {
    delay(3000)
    println("[suspendTask1] After 3s in (${Thread.currentThread().name})")
    delay(3000)
    println("[suspendTask1] After 6s in (${Thread.currentThread().name})")

    println("[suspendTask1] END in (${Thread.currentThread().name})*****")
}
suspend fun suspendTask2() {
    delay(1000)
    println("[suspendTask2] After 1s in (${Thread.currentThread().name})")
    delay(3000)
    println("[suspendTask2] After 4s in (${Thread.currentThread().name})")

    println("[suspendTask2] END in (${Thread.currentThread().name}) *****")
}
fun nonSuspendTask1() {
    Thread.sleep(3000)
    println("[nonSuspendTask1] After 3s in (${Thread.currentThread().name})")
    Thread.sleep(3000)
    println("[nonSuspendTask1] After 6s in (${Thread.currentThread().name})")

    println("[nonSuspendTask1] END in (${Thread.currentThread().name}) *****")
}
fun nonSuspendTask2() {
    Thread.sleep(1000)
    println("[nonSuspendTask2] After 1s in (${Thread.currentThread().name})")
    Thread.sleep(3000)
    println("[nonSuspendTask2] After 4s in (${Thread.currentThread().name})")

    println("[nonSuspendTask2] END in (${Thread.currentThread().name})*****")
}