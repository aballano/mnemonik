package com.aballano.mnemonik

import com.aballano.mnemonik.tuples.Quadruple
import com.aballano.mnemonik.tuples.Quintuple
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

private const val DEFAULT_CAPACITY = 256

fun <A, R> ((A) -> R).memoize(
    initialCapacity: Int = DEFAULT_CAPACITY
): (A) -> R = memoize(HashMap(initialCapacity))

fun <A, R> ((A) -> R).memoize(
    cache: MutableMap<A, R>
): (A) -> R = { a: A ->
    cache.getOrPut(a) { this(a) }
}

fun <A, B, R> ((A, B) -> R).memoize(
    initialCapacity: Int = DEFAULT_CAPACITY
): (A, B) -> R = memoize(HashMap(initialCapacity))

fun <A, B, R> ((A, B) -> R).memoize(
    cache: MutableMap<Pair<A, B>, R>
): (A, B) -> R = { a: A, b: B ->
    cache.getOrPut(a to b) { this(a, b) }
}

fun <A, B, C, R> ((A, B, C) -> R).memoize(
    initialCapacity: Int = DEFAULT_CAPACITY
): (A, B, C) -> R = memoize(HashMap(initialCapacity))

fun <A, B, C, R> ((A, B, C) -> R).memoize(
    cache: MutableMap<Triple<A, B, C>, R>
): (A, B, C) -> R = { a: A, b: B, c: C ->
    cache.getOrPut(Triple(a, b, c)) { this(a, b, c) }
}

fun <A, B, C, D, R> ((A, B, C, D) -> R).memoize(
    initialCapacity: Int = DEFAULT_CAPACITY
): (A, B, C, D) -> R = memoize(HashMap(initialCapacity))

fun <A, B, C, D, R> ((A, B, C, D) -> R).memoize(
    cache: MutableMap<Quadruple<A, B, C, D>, R>
): (A, B, C, D) -> R = { a: A, b: B, c: C, d: D ->
    cache.getOrPut(Quadruple(a, b, c, d)) { this(a, b, c, d) }
}

fun <A, B, C, D, E, R> ((A, B, C, D, E) -> R).memoize(
    initialCapacity: Int = DEFAULT_CAPACITY
): (A, B, C, D, E) -> R = memoize(HashMap(initialCapacity))

fun <A, B, C, D, E, R> ((A, B, C, D, E) -> R).memoize(
    cache: MutableMap<Quintuple<A, B, C, D, E>, R>
): (A, B, C, D, E) -> R = { a: A, b: B, c: C, d: D, e: E ->
    cache.getOrPut(Quintuple(a, b, c, d, e)) { this(a, b, c, d, e) }
}

fun <A, R> (suspend (A) -> R).memoizeSuspend(
    initialCapacity: Int = DEFAULT_CAPACITY
): suspend (A) -> R = memoizeSuspend(ConcurrentHashMap(initialCapacity))

fun <A, R> (suspend (A) -> R).memoizeSuspend(
    cache: ConcurrentMap<A, R>
): suspend (A) -> R = { a: A ->
    cache.getOrPut(a) { this(a) }
}

fun <A, B, R> (suspend (A, B) -> R).memoizeSuspend(
    initialCapacity: Int = DEFAULT_CAPACITY
): suspend (A, B) -> R = memoizeSuspend(ConcurrentHashMap(initialCapacity))

fun <A, B, R> (suspend (A, B) -> R).memoizeSuspend(
    cache: ConcurrentMap<Pair<A, B>, R>
): suspend (A, B) -> R = { a: A, b: B ->
    cache.getOrPut(a to b) { this(a, b) }
}

fun <A, B, C, R> (suspend (A, B, C) -> R).memoizeSuspend(
    initialCapacity: Int = DEFAULT_CAPACITY
): suspend (A, B, C) -> R = memoizeSuspend(ConcurrentHashMap(initialCapacity))

fun <A, B, C, R> (suspend (A, B, C) -> R).memoizeSuspend(
    cache: ConcurrentMap<Triple<A, B, C>, R>
): suspend (A, B, C) -> R = { a: A, b: B, c: C ->
    cache.getOrPut(Triple(a, b, c)) { this(a, b, c) }
}

fun <A, B, C, D, R> (suspend (A, B, C, D) -> R).memoizeSuspend(
    initialCapacity: Int = DEFAULT_CAPACITY
): suspend (A, B, C, D) -> R = memoizeSuspend(ConcurrentHashMap(initialCapacity))

fun <A, B, C, D, R> (suspend (A, B, C, D) -> R).memoizeSuspend(
    cache: ConcurrentMap<Quadruple<A, B, C, D>, R>
): suspend (A, B, C, D) -> R = { a: A, b: B, c: C, d: D ->
    cache.getOrPut(Quadruple(a, b, c, d)) { this(a, b, c, d) }
}

fun <A, B, C, D, E, R> (suspend (A, B, C, D, E) -> R).memoizeSuspend(
    initialCapacity: Int = DEFAULT_CAPACITY
): suspend (A, B, C, D, E) -> R = memoizeSuspend(ConcurrentHashMap(initialCapacity))

fun <A, B, C, D, E, R> (suspend (A, B, C, D, E) -> R).memoizeSuspend(
    cache: ConcurrentMap<Quintuple<A, B, C, D, E>, R>
): suspend (A, B, C, D, E) -> R = { a: A, b: B, c: C, d: D, e: E ->
    cache.getOrPut(Quintuple(a, b, c, d, e)) { this(a, b, c, d, e) }
}
