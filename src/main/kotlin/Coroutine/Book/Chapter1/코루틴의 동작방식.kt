package Coroutine.Book.Chapter1

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {

    val time = measureTimeMillis {
        createCoroutines(5)
    }

    println("Took $time ms")
}

private suspend fun createCoroutines(amount: Int) {
    val jobs = ArrayList<Job>()
    repeat(amount) {
        jobs += CoroutineScope(Dispatchers.Default).launch {
            println("Started $it : ${Thread.currentThread().name} ")
            delay(1000)
            println("Finished $it : ${Thread.currentThread().name} ")
        }
    }

    for(job in jobs) {
        job.join()
    }
}