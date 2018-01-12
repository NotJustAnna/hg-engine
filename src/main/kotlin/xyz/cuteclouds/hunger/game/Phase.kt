package xyz.cuteclouds.hunger.game

abstract class Phase : Iterable<Phase> {
    abstract val game: Game

    open fun hasNext(): Boolean = true

    abstract fun next(): Phase

    override fun iterator(): Iterator<Phase> = PhaseIterator(this)
}

