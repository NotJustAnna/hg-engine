package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.events.Events
import xyz.cuteclouds.hunger.events.TributePool
import xyz.cuteclouds.hunger.game.Event
import xyz.cuteclouds.hunger.game.Game
import xyz.cuteclouds.hunger.game.Phase
import xyz.cuteclouds.hunger.game.Tribute

class Night(
    override val game: Game,
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