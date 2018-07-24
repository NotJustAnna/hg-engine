package pw.aru.hungergames.phases

import pw.aru.hungergames.game.Game
import pw.aru.hungergames.game.Phase
import pw.aru.hungergames.game.Tribute

class FallenTributes(
    override val game: Game,
    val number: Int,
    val tributes: List<Tribute>,
    val fallenTributes: List<Tribute>
) : Phase() {
    override fun next() = Night.generate(game, number, tributes)
}