package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.game.Tribute
import xyz.cuteclouds.hunger.game.Game
import xyz.cuteclouds.hunger.game.Phase

class Winner(
    val game: Game,
    val winner: Tribute,
    val number: Int
) : Phase() {
    override fun hasNext() = false
    override fun next() = throw NoSuchElementException()
}