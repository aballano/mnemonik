package com.aballano.mnemonik

import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.concurrent.atomic.AtomicInteger

class MemoizeArity3Test : StringSpec() {

    private val counter = AtomicInteger(0)

    private fun concatSpaced(first: String, second: String, third: String): String =
        "$first $second $third".also { counter.incrementAndGet() }

    private suspend fun concatSpacedS(first: String, second: String, third: String): String =
        concatSpaced(first, second, third)

    private fun givenMemoizedFunction() = ::concatSpaced.memoize()
    private suspend fun givenMemoizedFunctionS() = ::concatSpacedS.memoizeSuspend()

    init {
        "memoized function should call only once with same params" {
            assertAll { a: String, b: String, c: String, d: String ->
                if (notEqual(a, b, c, d)) counter.givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a, b, c) shouldBe "$a $b $c"
                    concatSpacedMemoized(b, c, a) shouldBe "$b $c $a"
                    concatSpacedMemoized(a, d, d) shouldBe "$a $d $d"
                    concatSpacedMemoized(a, c, a) shouldBe "$a $c $a"
                    concatSpacedMemoized(a, b, c) shouldBe "$a $b $c"
                    counter.get() shouldBe 4
                }
            }
        }

        "memoized suspend function should call only once with same params" {
            assertAll { a: String, b: String, c: String, d: String ->
                if (notEqual(a, b, c, d)) counter.givenAllClearBlocking {
                    val concatSpacedMemoized = givenMemoizedFunctionS()
                    concatSpacedMemoized(a, b, c) shouldBe "$a $b $c"
                    concatSpacedMemoized(b, c, a) shouldBe "$b $c $a"
                    concatSpacedMemoized(a, d, d) shouldBe "$a $d $d"
                    concatSpacedMemoized(a, c, a) shouldBe "$a $c $a"
                    concatSpacedMemoized(a, b, c) shouldBe "$a $b $c"
                    counter.get() shouldBe 4
                }
            }
        }
    }
}
