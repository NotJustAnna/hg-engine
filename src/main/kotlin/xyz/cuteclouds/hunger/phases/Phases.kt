package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.data.Tribute
import xyz.cuteclouds.hunger.events.Event
import xyz.cuteclouds.hunger.events.Events
import xyz.cuteclouds.hunger.events.TributePool

abstract class Phase : Iterable<Phase> {
    open fun hasNext(): Boolean = true
    abstract fun next(): Phase

    override fun iterator(): Iterator<Phase> = PhaseIterator(this)
}

class PhaseIterator(var phase: Phase) : Iterator<Phase> {
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

class Bloodbath(
    private val game: Game,
    val events: List<Event>,
    private val tributes: List<Tribute>,
    private val fallenTributes: List<Tribute>
) : Phase() {

    override fun next() = Day.generate(game, 0, tributes, fallenTributes)

    companion object {
        fun generate(game: Game): Phase {
            val events = Events.generate(
                TributePool(game.tributes),
                game.actions.bloodbathHarmless,
                game.actions.bloodbathHarmful,
                game.thresholdSqrt,
                game.random
            )

            val (tributes, fallenTributes) = Events.compute(events)
            game.deathList += fallenTributes

            return Bloodbath(game, events, tributes, fallenTributes)
        }
    }
}

class Day(
    private val game: Game,
    val number: Int,
    val events: List<Event>,
    private val tributes: List<Tribute>,
    private val fallenTributes: List<Tribute>
) : Phase() {
    override fun next() = FallenTributes(game, number, tributes, fallenTributes)

    companion object {

        fun generate(game: Game, number: Int, tributes: List<Tribute>, alreadyDead: List<Tribute>, wasFeast: Boolean = false): Phase {
            if (tributes.size <= 1) return FallenTributes(game,  number, tributes, alreadyDead)

            if (!wasFeast && number > 2 && number % 2 == 0 && game.random.nextDouble() < game.thresholdSqrt) {
                return Feast.generate(game, number, tributes, alreadyDead)
            }

            val events = Events.generate(
                TributePool(tributes),
                game.actions.dayHarmless,
                game.actions.dayHarmful,
                game.threshold,
                game.random
            )

            val (alive, fallenTributes) = Events.compute(events)
            game.deathList += fallenTributes

            return Day(game, number + 1, events, alive, arrayListOf(alreadyDead, fallenTributes).flatten())
        }

    }
}

class Feast(
    private val game: Game,
    val number: Int,
    val events: List<Event>,
    private val tributes: List<Tribute>,
    private val fallenTributes: List<Tribute>
) : Phase() {
    override fun next() = Day.generate(game, number, tributes, fallenTributes, wasFeast = true)

    companion object {
        fun generate(game: Game, number: Int, tributes: List<Tribute>, alreadyDead: List<Tribute>): Phase {
            // you never know...
            if (tributes.size <= 1) return FallenTributes(game,  number, tributes, alreadyDead)

            val events = Events.generate(
                TributePool(tributes),
                game.actions.feastHarmless,
                game.actions.feastHarmful,
                game.threshold,
                game.random
            )

            val (alive, fallenTributes) = Events.compute(events)
            game.deathList += fallenTributes

            return Feast(game, number + 1, events, alive, arrayListOf(alreadyDead, fallenTributes).flatten())
        }
    }
}

class FallenTributes(
    private val game: Game,
    val number: Int,
    val tributes: List<Tribute>,
    val fallenTributes: List<Tribute>
) : Phase() {
    override fun next() = Night.generate(game, number, tributes)
}

class Night(
    private val game: Game,
    val number: Int,
    val events: List<Event>,
    private val tributes: List<Tribute>,
    private val fallenTributes: List<Tribute>
) : Phase() {
    override fun next() = Day.generate(game, number, tributes, fallenTributes)

    companion object {
        fun generate(game: Game, number: Int, tributes: List<Tribute>): Phase {
            if (tributes.isEmpty()) return Draw(game, number)
            if (tributes.size == 1) return Winner(game, tributes.first(), number)

            val events = Events.generate(
                TributePool(tributes),
                game.actions.nightHarmless,
                game.actions.nightHarmful,
                game.threshold,
                game.random
            )

            val (alive, fallenTributes) = Events.compute(events)
            game.deathList += fallenTributes

            return Night(game, number, events, alive, fallenTributes)
        }
    }
}

class Winner(
    val game: Game,
    val winner: Tribute,
    val number: Int
) : Phase() {
    override fun hasNext() = false
    override fun next() = throw NoSuchElementException()
}

class Draw(
    val game: Game,
    val number: Int
) : Phase() {
    override fun hasNext() = false
    override fun next() = throw NoSuchElementException()
}