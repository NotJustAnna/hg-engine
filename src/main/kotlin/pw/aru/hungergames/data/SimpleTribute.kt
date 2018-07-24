package pw.aru.hungergames.data

import pw.aru.hungergames.game.Tribute

data class SimpleTribute(
    override val name: String
) : Tribute() {
    override fun copy(): Tribute = SimpleTribute(name)
}