package com.aballano.mnemonik.sample

import com.aballano.mnemonik.memoize
import kotlin.system.measureTimeMillis

const val MAX_CACHE_SIZE = 40

fun main() {
    // Considerably inefficient implementation for test purposes
    fun fib(k: Int): Long = when (k) {
        0 -> 1
        1 -> 1
        else -> fib(k - 1) + fib(k - 2)
    }

    val map = HashMap<Int, Long>(MAX_CACHE_SIZE)
    val memoizedFib = ::fib.memoize(cache = map)

    println("Test for range 1-$MAX_CACHE_SIZE")
    print("1st iteration: ")
    var totalMs = measureTimeMillis {
        (1..MAX_CACHE_SIZE).forEach {
            memoizedFib(it)
        }
    }
    print("$totalMs ms")
    println()
    println()
    print("2nd iteration: ")

    totalMs = measureTimeMillis {
        (1..MAX_CACHE_SIZE).forEach {
            memoizedFib(it)
        }
    }
    print("$totalMs ms")
    check(totalMs == 0L) { "2nd iteration time should be 0" }

    // Free cache
    map.clear()
}
