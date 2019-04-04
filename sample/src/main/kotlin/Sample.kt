package com.aballano.mnemonik.sample

import com.aballano.mnemonik.memoize
import kotlin.system.measureTimeMillis

const val MAX_NUMBER = 40

fun main() {
    val memoizedFib = ::fib.memoize(MAX_NUMBER)

    println("Test for range 1-$MAX_NUMBER")
    print("1st iteration: ")
    var totalMs = measureTimeMillis {
        (1..MAX_NUMBER).forEach {
            memoizedFib(it)
        }
    }
    print("$totalMs ms")
    println()
    println()
    print("2nd iteration: ")
    totalMs = measureTimeMillis {
        (1..MAX_NUMBER).forEach {
            memoizedFib(it)
        }
    }
    print("$totalMs ms")
}

// Considerably inefficient implementation for test purposes
fun fib(k: Int): Long = when (k) {
    0 -> 1
    1 -> 1
    else -> fib(k - 1) + fib(k - 2)
}
