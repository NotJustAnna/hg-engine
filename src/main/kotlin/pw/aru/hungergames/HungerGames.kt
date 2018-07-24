package pw.aru.hungergames

import pw.aru.hungergames.game.Actions
import pw.aru.hungergames.game.Game
import pw.aru.hungergames.game.Phase
import pw.aru.hungergames.game.Tribute
import pw.aru.hungergames.phases.Bloodbath
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