package pw.aru.hg.engine.game

abstract class Phase : Iterable<Phase> {
    abstract val game: Game

    open fun hasNext(): Boolean = true

    abstract fun next(): Phase

    override fun iterator(): Iterator<Phase> = PhaseIterator(this)
}

