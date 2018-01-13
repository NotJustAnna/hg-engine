package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.events.Events
import xyz.cuteclouds.hunger.events.TributePool
import xyz.cuteclouds.hunger.game.Event
import xyz.cuteclouds.hunger.game.Game
import xyz.cuteclouds.hunger.game.Phase
import xyz.cuteclouds.hunger.game.Tribute

class Day(
    override val game: Game,
    val number: Int,
    val events: List<Event>,
    private val tributes: List<Tribute>,
    private val fallenTributes: List<Tribute>
) : Phase() {
    override fun next() = FallenTributes(game, number, tributes, fallenTributes)

    companion object {

        fun generate(game: Game, number: Int, tributes: List<Tribute>, alreadyDead: List<Tribute>, wasFeast: Boolean = false): Phase {
            if (tributes.size <= 1) return FallenTributes(game, number, tributes, alreadyDead)

            if (!wasFeast && number > 2 && number % 2 == 0 && game.random.nextDouble() < game.getThresholdDown(tributes)) {
                return Feast.generate(game, number, tributes, alreadyDead)
            }

            val events = Events.generate(
                TributePool(tributes),
                game.actions.dayHarmless,
                game.actions.dayHarmful,
                game.getThresholdUp(tributes),
                game.random
            )

            val (alive, fallenTributes) = Events.compute(events)
            game.deathList += fallenTributes

            return Day(game, if (wasFeast) number else number + 1, events, alive, arrayListOf(alreadyDead, fallenTributes).flatten())
        }

    }
}