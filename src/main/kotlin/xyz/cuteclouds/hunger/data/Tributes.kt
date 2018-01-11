package xyz.cuteclouds.hunger.data

abstract class Tribute {
    abstract val name: String
    var kills: Int = 0
    val attributes = LinkedHashSet<String>()

    override fun toString() = name

    abstract fun copy() : Tribute
}

data class SimpleTribute(
    override val name: String
) : Tribute() {
    override fun copy(): Tribute = SimpleTribute(name)
}