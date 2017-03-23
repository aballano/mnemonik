import aballano.kotlinmemoization.memoize

const val MAX_NUMBER = 40

fun main(args: Array<String>) {
    val memoizedFib = ::fib.memoize(MAX_NUMBER)

    println("Test for range 1-$MAX_NUMBER")
    print("1st iteration: ")
    var start = System.currentTimeMillis()
    (1 .. MAX_NUMBER).forEach {
        memoizedFib(it)
    }
    print("${System.currentTimeMillis() - start} ms")
    println()
    println()
    print("2nd iteration: ")
    start = System.currentTimeMillis()
    (1 .. MAX_NUMBER).forEach {
        memoizedFib(it)
    }
    print("${System.currentTimeMillis() - start} ms")
}

// Considerably inefficient implementation for test purposes
fun fib(k: Int): Long = when (k) {
    0 -> 1
    1 -> 1
    else -> fib(k - 1) + fib(k - 2)
}
