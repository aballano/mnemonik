package com.aballano.mnemonik

import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.util.concurrent.atomic.AtomicInteger

class MemoizeArity1Test : StringSpec() {

    private val counter = AtomicInteger(0)

    private fun givenAllClear(block: () -> Unit) =
        counter.set(0).also { block() }

    private fun concatSpaced(first: String): String =
        "$first ".also { counter.incrementAndGet() }

    private fun givenMemoizedFunction() = ::concatSpaced.memoize()

    init {
        "normal function should call every time with same params" {
            assertAll { a: String ->
                givenAllClear {
                    concatSpaced(a) shouldBe "$a "
                    concatSpaced(a) shouldBe "$a "
                    concatSpaced(a) shouldBe "$a "
                    counter.get() shouldBe 3
                }
            }
        }

        "memoized function should call only once with same params" {
            assertAll { a: String ->
                givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a) shouldBe "$a "
                    concatSpacedMemoized(a) shouldBe "$a "
                    concatSpacedMemoized(a) shouldBe "$a "
                    counter.get() shouldBe 1
                }
            }
        }

        "memoized function should call every time with different params" {
            assertAll { a: String, b: String, c: String ->
                if (notEqual(a, b, c)) givenAllClear {
                    val concatSpacedMemoized = givenMemoizedFunction()
                    concatSpacedMemoized(a) shouldBe "$a "
                    concatSpacedMemoized(b) shouldBe "$b "
                    concatSpacedMemoized(c) shouldBe "$c "
                    concatSpacedMemoized(a) shouldBe "$a "
                    counter.get() shouldBe 3
                }
            }
        }
    }
}
