package test

import com.aballano.mnemonik.memoize
import org.openjdk.jmh.annotations.*

@State(Scope.Benchmark)
@Fork(1)
class MemoizeBenchmark {

    private lateinit var mFib: (k: Int) -> Long

    // Considerably inefficient implementation for test purposes
    private fun fib(k: Int): Long = when (k) {
        0 -> 1
        1 -> 1
        else -> fib(k - 1) + fib(k - 2)
    }

    @Setup
    fun setUp() {
        mFib = ::fib.memoize()
    }

    @Benchmark
    fun normalFunctionCall() {
        fib(5)
    }

    @Benchmark
    fun memoizedFunctionCall() {
        mFib(5)
    }
}