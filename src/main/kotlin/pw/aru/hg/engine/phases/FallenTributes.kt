package pw.aru.hg.engine.phases

import pw.aru.hg.engine.game.Game
import pw.aru.hg.engine.game.Phase
import pw.aru.hg.engine.game.Tribute

class FallenTributes(
    override val game: Game,
    val number: Int,
    val tributes: List<Tribute>,
    val fallenTributes: List<Tribute>
) : Phase() {
    override fun next() = Night.generate(game, number, tributes)
}