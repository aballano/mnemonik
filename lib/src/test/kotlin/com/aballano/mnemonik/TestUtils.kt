package com.aballano.mnemonik

import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

fun notEqual(vararg strings: String): Boolean =
    strings.groupingBy { it }.eachCount().all { it.value == 1 }

fun AtomicInteger.givenAllClear(block: () -> Unit) =
    set(0).also { block() }

fun AtomicInteger.givenAllClearBlocking(block: suspend () -> Unit) =
    set(0).also { runBlocking { block() } }