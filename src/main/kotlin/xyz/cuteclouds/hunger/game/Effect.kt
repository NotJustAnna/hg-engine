package xyz.cuteclouds.hunger.game

data class Effect(
    val add: List<String> = emptyList(),
    val remove: List<String> = emptyList()
) {
    companion object {
        val empty = Effect()
    }
}