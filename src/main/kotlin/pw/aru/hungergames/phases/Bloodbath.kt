package pw.aru.hungergames.phases

import pw.aru.hungergames.events.Events
import pw.aru.hungergames.events.TributePool
import pw.aru.hungergames.game.Event
import pw.aru.hungergames.game.Game
import pw.aru.hungergames.game.Phase
import pw.aru.hungergames.game.Tribute

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