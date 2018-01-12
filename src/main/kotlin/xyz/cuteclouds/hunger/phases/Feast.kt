package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.game.Tribute
import xyz.cuteclouds.hunger.game.Event
import xyz.cuteclouds.hunger.events.Events
import xyz.cuteclouds.hunger.events.TributePool
import xyz.cuteclouds.hunger.game.Game
import xyz.cuteclouds.hunger.game.Phase

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
            if (tributes.size <= 1) return FallenTributes(game, number, tributes, alreadyDead)

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