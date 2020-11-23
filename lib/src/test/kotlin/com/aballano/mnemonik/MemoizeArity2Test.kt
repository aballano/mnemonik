package com.aballano.mnemonik

import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.concurrent.atomic.AtomicInteger

class MemoizeArity2Test : StringSpec() {

    private val counter = AtomicInteger(0)

    private fun concatSpaced(first: String, second: String): String =
        "$first $second".also { counter.incrementAndGet() }

    private suspend fun concatSpacedS(first: String, second: String): String =
        concatSpaced(first, second)

    private fun givenMemoizedFunction() = ::concatSpaced.memoize()
    private fun givenMemoizedFunctionS() = ::concatSpacedS.memoizeSuspend()

    init {
        "memoized function should call only once with same params" {
            assertAll { a: String, b: String, c: String, d: String ->
                if (notEqual(a, b, c, d)) counter.givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    concatSpacedMemoized(b, c) shouldBe "$b $c"
                    concatSpacedMemoized(b, a) shouldBe "$b $a"
                    concatSpacedMemoized(a, d) shouldBe "$a $d"
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    counter.get() shouldBe 4
                }
            }
        }

        "memoized suspend function should call only once with same params" {
            assertAll { a: String, b: String, c: String, d: String ->
                if (notEqual(a, b, c, d)) counter.givenAllClearBlocking {
                    val concatSpacedMemoized = givenMemoizedFunctionS()
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    concatSpacedMemoized(b, c) shouldBe "$b $c"
                    concatSpacedMemoized(b, a) shouldBe "$b $a"
                    concatSpacedMemoized(a, d) shouldBe "$a $d"
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    counter.get() shouldBe 4
                }
            }
        }
    }
}
