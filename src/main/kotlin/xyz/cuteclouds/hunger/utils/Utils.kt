package xyz.cuteclouds.hunger.utils

inline fun <T, C : MutableCollection<in T>> Iterable<T>.splitIndexedTo(destination1: C, destination2: C, predicate: (index: Int, T) -> Boolean): Pair<C, C> {
    forEachIndexed { index, element ->
        (if (predicate(index, element)) destination1 else destination2).add(element)
    }
    return Pair(destination1, destination2)
}

inline fun <T> Iterable<T>.splitIndexed(predicate: (index: Int, T) -> Boolean): Pair<List<T>, List<T>> {
    return splitIndexedTo(ArrayList<T>(), ArrayList<T>(), predicate)
}