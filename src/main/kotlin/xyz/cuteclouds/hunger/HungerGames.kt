package xyz.cuteclouds.hunger

import xyz.cuteclouds.hunger.game.Actions
import xyz.cuteclouds.hunger.game.Game
import xyz.cuteclouds.hunger.game.Phase
import xyz.cuteclouds.hunger.game.Tribute
import xyz.cuteclouds.hunger.phases.Bloodbath
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