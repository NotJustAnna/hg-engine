package xyz.cuteclouds.hunger.game

abstract class Phase : Iterable<Phase> {
    open fun hasNext(): Boolean = true
    abstract fun next(): Phase

    override fun iterator(): Iterator<Phase> = PhaseIterator(this)
}

