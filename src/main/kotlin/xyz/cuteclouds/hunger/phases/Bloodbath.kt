package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.events.Events
import xyz.cuteclouds.hunger.events.TributePool
import xyz.cuteclouds.hunger.game.Event
import xyz.cuteclouds.hunger.game.Game
import xyz.cuteclouds.hunger.game.Phase
import xyz.cuteclouds.hunger.game.Tribute

class Bloodbath(
    override val game: Game,
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
                game.getThresholdUp(game.tributes, 0),
                game.random
            )

            val (tributes, fallenTributes) = Events.compute(events)
            game.deathList += fallenTributes

            return Bloodbath(game, events, tributes, fallenTributes)
        }
    }
}