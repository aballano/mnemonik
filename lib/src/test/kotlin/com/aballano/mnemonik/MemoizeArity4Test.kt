package com.aballano.mnemonik

import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.concurrent.atomic.AtomicInteger

class MemoizeArity4Test : StringSpec() {

    private val counter = AtomicInteger(0)

    private fun concatSpaced(first: String, second: String, third: String, fourth: String): String =
        "$first $second $third $fourth".also { counter.incrementAndGet() }

    private suspend fun concatSpacedS(first: String, second: String, third: String, fourth: String): String =
        concatSpaced(first, second, third, fourth)

    private fun givenMemoizedFunction() = ::concatSpaced.memoize()
    private fun givenMemoizedFunctionS() = ::concatSpacedS.memoizeSuspend()

    init {
        "memoized function should call only once with same params" {
            assertAll { a: String, b: String, c: String, d: String, e: String, f: String ->
                if (notEqual(a, b, c, d, e, f)) counter.givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    concatSpacedMemoized(b, c, a, d) shouldBe "$b $c $a $d"
                    concatSpacedMemoized(a, d, e, c) shouldBe "$a $d $e $c"
                    concatSpacedMemoized(a, d, d, f) shouldBe "$a $d $d $f"
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    counter.get() shouldBe 4
                }
            }
        }

        "memoized suspend function should call only once with same params" {
            assertAll { a: String, b: String, c: String, d: String, e: String, f: String ->
                if (notEqual(a, b, c, d, e, f)) counter.givenAllClearBlocking {
                    val concatSpacedMemoized = givenMemoizedFunctionS()
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    concatSpacedMemoized(b, c, a, d) shouldBe "$b $c $a $d"
                    concatSpacedMemoized(a, d, e, c) shouldBe "$a $d $e $c"
                    concatSpacedMemoized(a, d, d, f) shouldBe "$a $d $d $f"
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    counter.get() shouldBe 4
                }
            }
        }
    }
}
