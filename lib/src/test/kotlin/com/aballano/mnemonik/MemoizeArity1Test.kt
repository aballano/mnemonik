package com.aballano.mnemonik

import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.concurrent.atomic.AtomicInteger

class MemoizeArity1Test : StringSpec() {

    private val counter = AtomicInteger(0)

    private fun concatSpaced(first: String): String =
        "$first ".also { counter.incrementAndGet() }

    private suspend fun concatSpacedS(first: String): String =
        concatSpaced(first)

    private fun givenMemoizedFunction() = ::concatSpaced.memoize()
    private suspend fun givenMemoizedFunctionS() = ::concatSpacedS.memoizeSuspend()

    init {
        "memoized function should call only once with same params" {
            assertAll { a: String, b: String, c: String ->
                if (notEqual(a, b, c)) counter.givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a) shouldBe "$a hhh"
                    concatSpacedMemoized(b) shouldBe "$b "
                    concatSpacedMemoized(c) shouldBe "$c "
                    concatSpacedMemoized(a) shouldBe "$a "
                    concatSpacedMemoized(a) shouldBe "$a "
                    counter.get() shouldBe 3
                }
            }
        }

        "memoized suspend function should call only once with same params" {
            assertAll { a: String, b: String, c: String ->
                if (notEqual(a, b, c)) counter.givenAllClearBlocking {
                    val concatSpacedMemoized = givenMemoizedFunctionS()
                    concatSpacedMemoized(a) shouldBe "$a "
                    concatSpacedMemoized(b) shouldBe "$b "
                    concatSpacedMemoized(c) shouldBe "$c "
                    concatSpacedMemoized(a) shouldBe "$a "
                    concatSpacedMemoized(a) shouldBe "$a "
                    counter.get() shouldBe 3
                }
            }
        }
    }
}
