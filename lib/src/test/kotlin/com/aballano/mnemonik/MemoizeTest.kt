package com.aballano.mnemonik

import io.kotlintest.*
import io.kotlintest.properties.assertAll
import io.kotlintest.specs.StringSpec
import java.util.concurrent.atomic.AtomicInteger

class MemoizeTest : StringSpec() {

    private val counter = AtomicInteger(0)

    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
        counter.set(0)
    }

    private fun concatSpaced(first: String, second: String): String =
        "$first $second".also { counter.incrementAndGet() }

    private fun givenMemoizedFunction() = ::concatSpaced.memoize()

    init {
        "normal function should call every time with same params" {
            concatSpaced("hello", "world") shouldBe "hello world"
            concatSpaced("hello", "world") shouldBe "hello world"
            concatSpaced("hello", "world") shouldBe "hello world"
            counter.get() shouldBe 3
        }

        "memoized function should call only once with same params" {
            val concatSpacedMemoized = givenMemoizedFunction()
            concatSpacedMemoized("hello", "world") shouldBe "hello world"
            concatSpacedMemoized("hello", "world") shouldBe "hello world"
            concatSpacedMemoized("hello", "world") shouldBe "hello world"
            counter.get() shouldBe 1
        }

//        "memoized function should call only once with same params2" {
//            assertAll { a: String, b: String ->
//                if (a != b) {
//                    println("a:$a~b:$b~")
//                    a.hashCode() shouldNotBe b.hashCode()
//                    val concatSpacedMemoized = givenMemoizedFunction()
//                    concatSpacedMemoized(a, b) shouldBe "$a $b"
//                    concatSpacedMemoized(a, b) shouldBe "$a $b"
//                    concatSpacedMemoized(a, b) shouldBe "$a $b"
//                    counter.get() shouldBe 1
//                }
//            }
//        }

        "memoized function should call only every time with different params" {
            val concatSpacedMemoized = givenMemoizedFunction()
            concatSpacedMemoized("1", "2") shouldBe "1 2"
            concatSpacedMemoized("2", "3") shouldBe "2 3"
            concatSpacedMemoized("1", "5") shouldBe "1 5"
            counter.get() shouldBe 3
        }

        "memoized function should call only every time with switched params" {
            val concatSpacedMemoized = givenMemoizedFunction()
            concatSpacedMemoized("hello", "world") shouldBe "hello world"
            concatSpacedMemoized("world", "hello") shouldBe "world hello"
            counter.get() shouldBe 2
        }

    }
}

//private fun TestContext.assertAll2(function: (String, String) -> Unit) {
//    function("", "a")
//}
