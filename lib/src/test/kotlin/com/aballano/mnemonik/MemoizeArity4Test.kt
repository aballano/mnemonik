package com.aballano.mnemonik

import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.concurrent.atomic.AtomicInteger

class MemoizeArity4Test : StringSpec() {

    private val counter = AtomicInteger(0)

    private fun givenAllClear(block: () -> Unit) =
        counter.set(0).also { block() }

    private fun concatSpaced(first: String, second: String, third: String, fourth: String): String =
        "$first $second $third $fourth".also { counter.incrementAndGet() }

    private fun givenMemoizedFunction() = ::concatSpaced.memoize()

    init {
        "normal function should call every time with same params" {
            assertAll { a: String, b: String, c: String, d: String ->
                givenAllClear {
                    concatSpaced(a, b, c, d) shouldBe "$a $b $c $d"
                    concatSpaced(a, b, c, d) shouldBe "$a $b $c $d"
                    concatSpaced(a, b, c, d) shouldBe "$a $b $c $d"
                    counter.get() shouldBe 3
                }
            }
        }

        "memoized function should call only once with same params" {
            assertAll { a: String, b: String, c: String, d: String ->
                givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    counter.get() shouldBe 1
                }
            }
        }

        "memoized function should call every time with different params" {
            assertAll { a: String, b: String, c: String, d: String, e: String, f: String ->
                if (notEqual(a, b, c, d, e, f)) givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    concatSpacedMemoized(b, c, a, d) shouldBe "$b $c $a $d"
                    concatSpacedMemoized(a, d, e, c) shouldBe "$a $d $e $c"
                    concatSpacedMemoized(a, d, d, f) shouldBe "$a $d $d $f"
                    concatSpacedMemoized(a, b, c, d) shouldBe "$a $b $c $d"
                    counter.get() shouldBe 4
                }
            }
        }
    }
}
