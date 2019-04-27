package pw.aru.hg.engine.game

import pw.aru.hg.engine.events.EventFormatter

abstract class Tribute {
    abstract val name: String
    var kills: Int = 0
    val attributes = LinkedHashSet<String>()

    override fun toString() = name

    fun format(formatter: EventFormatter): String = formatter.format(this)

    abstract fun copy() : Tribute
}

