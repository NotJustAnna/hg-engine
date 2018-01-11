package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.data.HarmfulAction
import xyz.cuteclouds.hunger.data.HarmlessAction
import xyz.cuteclouds.hunger.data.Tribute
import java.util.*
import kotlin.math.sqrt

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

data class Game
internal constructor(
    val tributes: List<Tribute>,
    val actions: Actions,
    val threshold: Double,
    val random: Random
) {
    val deathList = LinkedList<Tribute>()
    val thresholdSqrt = Math.floor(sqrt(threshold) * 10.0) / 10.0
}

data class Actions(
    @JvmField val bloodbathHarmless: List<HarmlessAction>,
    @JvmField val bloodbathHarmful: List<HarmfulAction>,

    @JvmField val dayHarmless: List<HarmlessAction>,
    @JvmField val dayHarmful: List<HarmfulAction>,

    @JvmField val nightHarmless: List<HarmlessAction>,
    @JvmField val nightHarmful: List<HarmfulAction>,

    @JvmField val feastHarmless: List<HarmlessAction>,
    @JvmField val feastHarmful: List<HarmfulAction>
)