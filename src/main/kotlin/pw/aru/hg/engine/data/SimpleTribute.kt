package pw.aru.hg.engine.data

import pw.aru.hg.engine.game.Tribute

data class SimpleTribute(
    override val name: String
) : Tribute() {
    override fun copy(): Tribute = SimpleTribute(name)
}