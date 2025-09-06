package com.milesilac.classreadingstats.helpers

inline fun <T, R> Iterable<T>.mapPartition(
    transform: (T) -> R,
    predicate: (R) -> Boolean
): Pair<List<R>, List<R>> {
    val a = mutableListOf<R>()
    val b = mutableListOf<R>()
    for (item in this) {
        val mapped = transform(item)
        if (predicate(mapped)) {
            a += mapped
        } else {
            b += mapped
        }
    }
    return Pair(a, b)
}