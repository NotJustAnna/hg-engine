package xyz.cuteclouds.hunger.game

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

    val thresholdSqrt = threshold.sqrt().floor(100.0)
    val thresholdRt4 = threshold.pow(0.125).floor(100.0)

    fun getThresholdUp(tributes: List<Tribute>): Double {
        val base = if (tributes.size == this.tributes.size) 1.0 else tributes.size.toDouble() / this.tributes.size.toDouble()
        return ((base.sin() * thresholdRt4 + base * 4 + 4) / 9).floor(100.0)
    }

    fun getThresholdDown(tributes: List<Tribute>): Double {
        return 1 - getThresholdUp(tributes)
    }
}

private fun Double.sin() = Math.sin(this)

private fun Double.sqrt() = Math.sqrt(this)

private fun Double.floor(factor: Double = 1.0) = Math.floor(this * factor) / factor

