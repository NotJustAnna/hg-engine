package xyz.cuteclouds.hunger.game

class PhaseIterator(var phase: Phase) : Iterator<Phase>, Iterable<Phase> {
    override fun iterator(): Iterator<Phase> = this

    private var first: Boolean = true

    override fun hasNext(): Boolean = first || phase.hasNext()

    override fun next(): Phase {
        if (first) {
            first = false
            return phase
        }

        phase = phase.next()
        return phase
    }
}