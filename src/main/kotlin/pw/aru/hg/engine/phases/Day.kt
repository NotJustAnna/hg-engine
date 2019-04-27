package pw.aru.hg.engine.phases

import pw.aru.hg.engine.events.Events
import pw.aru.hg.engine.events.TributePool
import pw.aru.hg.engine.game.Event
import pw.aru.hg.engine.game.Game
import pw.aru.hg.engine.game.Phase
import pw.aru.hg.engine.game.Tribute

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

            if (!wasFeast && number > 2 && number % 2 == 0 && game.random.nextDouble() < game.getThresholdDown(tributes, number)) {
                return Feast.generate(game, number, tributes, alreadyDead)
            }

            val events = Events.generate(
                TributePool(tributes),
                game.actions.dayHarmless,
                game.actions.dayHarmful,
                game.getThresholdUp(tributes, number),
                game.random
            )

            val (alive, fallenTributes) = Events.compute(events)
            game.deathList += fallenTributes

            return Day(game, if (wasFeast) number else number + 1, events, alive, arrayListOf(alreadyDead, fallenTributes).flatten())
        }
    }
}