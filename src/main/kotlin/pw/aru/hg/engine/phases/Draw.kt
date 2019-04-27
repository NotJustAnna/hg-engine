package pw.aru.hg.engine.phases

import pw.aru.hg.engine.game.Game
import pw.aru.hg.engine.game.Phase
import pw.aru.hg.engine.game.Tribute

class Draw(
    override val game: Game,
    val number: Int,
    val ranking: List<Tribute>
) : Phase() {
    override fun hasNext() = false
    override fun next() = throw NoSuchElementException()
}