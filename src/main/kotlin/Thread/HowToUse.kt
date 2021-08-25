package Thread

fun main() {
    val counterThread = CounterThread()
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