package pw.aru.hungergames.game

import pw.aru.hungergames.events.EventFormatter

abstract class Tribute {
    abstract val name: String
    var kills: Int = 0
    val attributes = LinkedHashSet<String>()

    override fun toString() = name

    fun format(formatter: EventFormatter): String = formatter.format(this)

    abstract fun copy() : Tribute
}

