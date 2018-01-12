package xyz.cuteclouds.hunger.phases

import xyz.cuteclouds.hunger.game.Game
import xyz.cuteclouds.hunger.game.Phase
import xyz.cuteclouds.hunger.game.Tribute

class FallenTributes(
    override val game: Game,
    val number: Int,
    val tributes: List<Tribute>,
    val fallenTributes: List<Tribute>
) : Phase() {
    override fun next() = Night.generate(game, number, tributes)
}