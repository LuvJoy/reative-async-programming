package Coroutine.Flow

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val numberFlow = fetchNumberFlow()
    val stringFlow = fetchStringFlow()

    val combinedFlow = numberFlow.combine(stringFlow) { number, string ->
        number to string
    }.onEach { print("User : ") }

    runBlocking {
        println("[start]")
        combinedFlow.collect {
            println(it)
        }
    }
}

// 숫자 리스트를 Flow로 변경한 경우
fun fetchNumberFlow(): Flow<Int> {
    val numbers = listOf(1,2,3,4,5)
    return numbers.asFlow()
}

// 문자 리스트를 Flow로 변경한 경우
fun fetchStringFlow(): Flow<String> {
    val strings = listOf("John", "Jim", "James", "Joseph", "Kim")
    return strings.asFlow()
}