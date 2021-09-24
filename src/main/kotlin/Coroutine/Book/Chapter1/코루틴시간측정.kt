package Coroutine.Book

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        createCoroutines(10_000)
    }
    println("Took $time ms")
}

suspend fun createCoroutines(amount: Int) {
    val jobs = ArrayList<Job>()
    repeat(amount) {
        jobs += CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
        }
    }

    for(job in jobs) {
        job.join()
    }
}