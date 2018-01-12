package xyz.cuteclouds.hunger.data

import xyz.cuteclouds.hunger.game.Tribute

data class SimpleTribute(
    override val name: String
) : Tribute() {
    override fun copy(): Tribute = SimpleTribute(name)
}