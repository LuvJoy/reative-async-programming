package Coroutine.Book.Chapter1

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("${Thread.activeCount()} threads active at the start")

    val time = measureTimeMillis {
        createCoroutines(10_000)
    }
    println("${Thread.activeCount()} threads active at the end")
    println("Took $time ms")
}

private suspend fun createCoroutines(amount: Int) {
    val jobs = ArrayList<Job>()
    repeat(amount) {
        jobs += CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
        }
    }

    for(job in jobs) {
        job.join()
    }
}