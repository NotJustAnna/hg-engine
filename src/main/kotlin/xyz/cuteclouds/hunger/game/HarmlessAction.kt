package xyz.cuteclouds.hunger.game

data class HarmlessAction(
    override val amount: Int,
    override val action: String,
    override val effects: Map<Int, Effect> = emptyMap(),
    override val requires: Map<Int, List<String>> = emptyMap()
) : Action()