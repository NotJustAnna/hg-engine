package pw.aru.hg.engine

import pw.aru.hg.engine.game.Actions
import pw.aru.hg.engine.game.Game
import pw.aru.hg.engine.game.Phase
import pw.aru.hg.engine.game.Tribute
import pw.aru.hg.engine.phases.Bloodbath
import java.util.*

data class HungerGames(
    val tributes: List<Tribute>,
    val actions: Actions,
    val threshold: Double,
    val random: Random = Random()
) : Iterable<Phase> {
    init {
        check(threshold > 0 && threshold < 1) { "Threshold needs to be between 0 and 1." }
    }

    fun newGame() : Phase = Bloodbath.generate(Game(tributes.map(Tribute::copy), actions, threshold, random))

    override fun iterator(): Iterator<Phase> = newGame().iterator()
}