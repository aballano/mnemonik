package com.aballano.mnemonik

import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.concurrent.atomic.AtomicInteger

class MemoizeArity2Test : StringSpec() {

    private val counter = AtomicInteger(0)

    private fun givenAllClear(block: () -> Unit) =
        counter.set(0).also { block() }

    private fun concatSpaced(first: String, second: String): String =
        "$first $second".also { counter.incrementAndGet() }

    private fun givenMemoizedFunction() = ::concatSpaced.memoize()

    init {
        "normal function should call every time with same params" {
            assertAll { a: String, b: String ->
                givenAllClear {
                    concatSpaced(a, b) shouldBe "$a $b"
                    concatSpaced(a, b) shouldBe "$a $b"
                    concatSpaced(a, b) shouldBe "$a $b"
                    counter.get() shouldBe 3
                }
            }
        }

        "memoized function should call only once with same params" {
            assertAll { a: String, b: String ->
                givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    counter.get() shouldBe 1
                }
            }
        }

        "memoized function should call every time with different params" {
            assertAll { a: String, b: String, c: String, d: String ->
                if (notEqual(a, b, c, d)) givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    concatSpacedMemoized(b, c) shouldBe "$b $c"
                    concatSpacedMemoized(b, a) shouldBe "$b $a"
                    concatSpacedMemoized(a, d) shouldBe "$a $d"
                    concatSpacedMemoized(a, b) shouldBe "$a $b"
                    counter.get() shouldBe 4
                }
            }
        }
    }
}
