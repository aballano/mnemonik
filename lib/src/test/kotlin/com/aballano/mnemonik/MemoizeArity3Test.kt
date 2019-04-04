package com.aballano.mnemonik

import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.concurrent.atomic.AtomicInteger

class MemoizeArity3Test : StringSpec() {

    private val counter = AtomicInteger(0)

    private fun givenAllClear(block: () -> Unit) =
        counter.set(0).also { block() }

    private fun concatSpaced(first: String, second: String, third: String): String =
        "$first $second $third".also { counter.incrementAndGet() }

    private fun givenMemoizedFunction() = ::concatSpaced.memoize()

    init {
        "normal function should call every time with same params" {
            assertAll { a: String, b: String, c: String ->
                givenAllClear {
                    concatSpaced(a, b, c) shouldBe "$a $b $c"
                    concatSpaced(a, b, c) shouldBe "$a $b $c"
                    concatSpaced(a, b, c) shouldBe "$a $b $c"
                    counter.get() shouldBe 3
                }
            }
        }

        "memoized function should call only once with same params" {
            assertAll { a: String, b: String, c: String ->
                givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a, b, c) shouldBe "$a $b $c"
                    concatSpacedMemoized(a, b, c) shouldBe "$a $b $c"
                    concatSpacedMemoized(a, b, c) shouldBe "$a $b $c"
                    counter.get() shouldBe 1
                }
            }
        }

        "memoized function should call every time with different params" {
            assertAll { a: String, b: String, c: String, d: String ->
                if (notEqual(a, b, c, d)) givenAllClear {
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
    }
}
