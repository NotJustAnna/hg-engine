package xyz.cuteclouds.hunger.data

sealed class Action {
    abstract val amount: Int
    abstract val action: String
    abstract val effects: Map<Int, Effect>
    abstract val requires: Map<Int, List<String>>
}

data class Effect(
    val add: List<String> = emptyList(),
    val remove: List<String> = emptyList()
) {
    companion object {
        val empty = Effect()
    }
}

data class HarmlessAction(
    override val amount: Int,
    override val action: String,
    override val effects: Map<Int, Effect> = emptyMap(),
    override val requires: Map<Int, List<String>> = emptyMap()
) : Action()

data class HarmfulAction(
    override val amount: Int,
    override val action: String,
    val killed: List<Int>,
    val killers: List<Int>,
    override val effects: Map<Int, Effect> = emptyMap(),
    override val requires: Map<Int, List<String>> = emptyMap()
) : Action()