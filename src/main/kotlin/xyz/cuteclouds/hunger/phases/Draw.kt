package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.game.Game
import xyz.cuteclouds.hunger.game.Phase

class Draw(
    override val game: Game,
    val number: Int
) : Phase() {
    override fun hasNext() = false
    override fun next() = throw NoSuchElementException()
}