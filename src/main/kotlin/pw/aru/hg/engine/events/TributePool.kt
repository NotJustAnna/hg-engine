package pw.aru.hg.engine.events

import pw.aru.hg.engine.game.Tribute
import java.util.*

class TributePool(tributes: List<Tribute>, private val r: Random = Random()) {
    private val pool: MutableList<Tribute> = ArrayList(tributes)

    val size: Int
        get() = pool.size

    val empty: Boolean
        get() = pool.isEmpty()

    fun select(function: Selector.() -> Unit) : Pair<Boolean,MutableList<Tribute>?> {
        val s = Selector()
        try {
            s.function()
            pool.removeAll(s.selected)
            return true to s.selected
        } catch (_ : SelectorNotFoundException) {
            return false to null
        }
    }

    inner class Selector {
        private val internalPool: MutableList<Tribute> = ArrayList(pool)
        internal val selected: MutableList<Tribute> = ArrayList()

        @JvmOverloads
        fun first(predicate: (Tribute) -> Boolean = { true }, amount: Int = 1, required: Boolean = true) : Boolean {
            val matches = internalPool.filter(predicate)
                .toList()

            if (matches.size < amount) {
                if (required) throw SelectorNotFoundException()
                else return false
            }

            val selection = matches.slice(0 until amount)
            internalPool.removeAll(selection)
            selected.addAll(selection)

            return true
        }

        @JvmOverloads
        fun random(predicate: (Tribute) -> Boolean = { true }, amount: Int = 1, required: Boolean = true) : Boolean {
            val matches = internalPool.filter(predicate)
                .toList()

            if (matches.size < amount) {
                if (required) throw SelectorNotFoundException()
                else return false
            }

            val selection = matches.shuffled(r).slice(0 until amount)
            internalPool.removeAll(selection)
            selected.addAll(selection)

            return true
        }
    }

    private class SelectorNotFoundException : RuntimeException()
}
