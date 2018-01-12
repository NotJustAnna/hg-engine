package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.game.Game
import xyz.cuteclouds.hunger.game.Phase
import xyz.cuteclouds.hunger.game.Tribute

class Winner(
    override val game: Game,
    val winner: Tribute,
    val number: Int
) : Phase() {
    override fun hasNext() = false
    override fun next() = throw NoSuchElementException()
}