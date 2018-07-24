package pw.aru.hungergames.phases

import pw.aru.hungergames.events.Events
import pw.aru.hungergames.events.TributePool
import pw.aru.hungergames.game.Event
import pw.aru.hungergames.game.Game
import pw.aru.hungergames.game.Phase
import pw.aru.hungergames.game.Tribute

class Feast(
    override val game: Game,
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
                game.getThresholdUp(tributes, number),
                game.random
            )

            val (alive, fallenTributes) = Events.compute(events)
            game.deathList += fallenTributes

            return Feast(game, number + 1, events, alive, arrayListOf(alreadyDead, fallenTributes).flatten())
        }
    }
}