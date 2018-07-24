package pw.aru.hungergames.game

import java.util.*
import kotlin.math.pow

data class Game(
    val tributes: List<Tribute>,
    val actions: Actions,
    val threshold: Double,
    val random: Random
) {
    init {
        check(threshold > 0 && threshold < 1) { "Threshold needs to be between 0 and 1." }
    }

    val deathList = LinkedList<Tribute>()
    private val thresholdRt4 = threshold.pow(0.125).floor(100.0)

    fun getThresholdUp(tributes: List<Tribute>, day: Int): Double {
        var base = if (tributes.size == this.tributes.size) 1.0 else tributes.size.toDouble() / this.tributes.size.toDouble()

        if (base == 1.0) base = 1.0 - day.toDouble().sigmoid()

        return ((base.sin() * thresholdRt4 + base * 4 + 4) / 9).floor(100.0)
    }

    fun getThresholdDown(tributes: List<Tribute>, day: Int): Double {
        return 1 - getThresholdUp(tributes, day)
    }
}

private fun Double.sin() = Math.sin(this)

private fun Double.floor(factor: Double = 1.0) = Math.floor(this * factor) / factor

private fun Double.sigmoid() = 1 / (1 + Math.exp(-this))