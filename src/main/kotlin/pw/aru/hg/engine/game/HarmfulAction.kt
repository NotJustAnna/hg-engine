package pw.aru.hg.engine.game

data class HarmfulAction(
    override val amount: Int,
    override val action: String,
    val killed: List<Int>,
    val killers: List<Int>,
    override val effects: Map<Int, Effect> = emptyMap(),
    override val requires: Map<Int, List<String>> = emptyMap()
) : Action()