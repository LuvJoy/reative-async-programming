package Thread

fun main() {
    val counterThread = CounterThread3()
    counterThread.start()
    Thread.sleep(2000L)
    counterThread.interrupt()
}

// [Case1] Interrupt 상태를 유지하려고 할 때
class CounterThread() : Thread() {
    override fun run() {
        super.run()

        try {
            repeat(10) {
                println("[${currentThread().name}] : $it")
                sleep(1000L)
            }
        } catch (e: InterruptedException) {
            println("[${currentThread().name}] : Interrupted Exception")
            println("[${currentThread().name}] : ${currentThread().isInterrupted}")
            currentThread().interrupt()
            println("[${currentThread().name}] : ${currentThread().isInterrupted}")
        }
    }
}

// [Case2] Interrupt처리가 잘못됐을때
class CounterThread2() : Thread() {
    override fun run() {
        super.run()

        var count = 1
        while(true) {
            println("[${currentThread().name}] : ${count++}")
            sleep(1000L)

            if(count == 7) {
                break
            }
        }
    }
}

// [Case2] Interrupt처리를 e.printStackTrace()로만 처리할 떄
class CounterThread3() : Thread() {
    override fun run() {
        super.run()

        var count = 1
        while(!currentThread().isInterrupted) {
            try {
                println("[${currentThread().name}] : ${count++}")
                sleep(1000L)

                if(count == 7) {
                    break
                }

            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}