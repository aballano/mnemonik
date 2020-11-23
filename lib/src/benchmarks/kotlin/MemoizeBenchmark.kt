package test

import com.aballano.mnemonik.memoize
import com.aballano.mnemonik.memoizeSuspend
import kotlinx.coroutines.runBlocking
import org.openjdk.jmh.annotations.*

@State(Scope.Benchmark)
@Fork(1)
class MemoizeBenchmark {

    val size = 100

    private lateinit var mSFib: suspend (n: Int) -> Long
    private lateinit var mFib: (k: Int) -> Long

    @Setup
    fun setUp() {
        mFib = ::fib.memoize()
        mSFib = ::sfib.memoizeSuspend()
    }

    @Benchmark
    fun normalFunctionCall() {
        fib(size)
    }

    @Benchmark
    fun memoizedFunctionCall() {
        mFib(size)
    }

    @Benchmark
    fun memoizedSuspendFunctionCall() = runBlocking {
        mSFib(size)
    }
}

// Considerably inefficient implementation for test purposes
private fun fib(n: Int): Long {
    val f = LongArray(n + 2) // 1 extra to handle case, n = 0

    // 0th and 1st number of the series are 0 and 1
    f[0] = 0
    f[1] = 1

    (2..n).forEach { i ->
        f[i] = f[i - 1] + f[i - 2]
    }

    return f[n]
}

private suspend fun sfib(n: Int): Long = fib(n)