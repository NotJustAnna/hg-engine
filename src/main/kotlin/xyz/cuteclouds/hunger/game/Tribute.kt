package xyz.cuteclouds.hunger.game

abstract class Tribute {
    abstract val name: String
    var kills: Int = 0
    val attributes = LinkedHashSet<String>()

    override fun toString() = name

    abstract fun copy() : Tribute
}

