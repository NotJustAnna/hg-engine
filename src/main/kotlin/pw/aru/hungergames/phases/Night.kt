package pw.aru.hungergames.phases

import pw.aru.hungergames.events.Events
import pw.aru.hungergames.events.TributePool
import pw.aru.hungergames.game.Event
import pw.aru.hungergames.game.Game
import pw.aru.hungergames.game.Phase
import pw.aru.hungergames.game.Tribute

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
            if (tributes.isEmpty()) return Draw(game, number, game.deathList.reversed())
            if (tributes.size == 1) {
                val winner = tributes.first()
                return Winner(game, winner, number, (winner + game.deathList.reversed()))
            }

            val events = Events.generate(
                TributePool(tributes),
                game.actions.nightHarmless,
                game.actions.nightHarmful,
                game.getThresholdUp(tributes, number),
                game.random
            )

            val (alive, fallenTributes) = Events.compute(events)
            game.deathList += fallenTributes

            return Night(game, number, events, alive, fallenTributes)
        }
    }
}

private operator fun <T> T.plus(list: List<T>): List<T> {
    val l = ArrayList<T>()
    l += this
    l += list
    return l
}
