package com.aballano.mnemonik

fun notEqual(vararg strings: String): Boolean =
    strings.groupingBy { it }.eachCount().all { it.value == 1 }