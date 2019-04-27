package pw.aru.hg.engine.events

import pw.aru.hg.engine.game.*
import java.util.*

object Events {

    fun generate(
        pool: TributePool,
        harmlessActions: List<HarmlessAction>,
        harmfulActions: List<HarmfulAction>,
        threshold: Double,
        r: Random = Random()
    ): List<Event> {
        val events = LinkedList<Event>()

        while (!pool.empty) {
            val event = consumeOnce(pool, if (r.nextDouble() < threshold) harmlessActions else harmfulActions, r)

            if (event != null) events.add(event)
        }

        return events
    }

    private fun consumeOnce(pool: TributePool, actions: List<Action>, r: Random): Event? {
        actions.shuffled(r).forEach { action ->
            if (pool.size < action.amount) return@forEach

            val (success, tributes) = pool.select {
                for (i in 0 until action.amount) {
                    random(
                        predicate = {
                            it.attributes.containsAll(action.requires.getOrElse(i, { emptyList() }))
                        }
                    )
                }
            }

            if (success) return Event.create(action, tributes!!)
        }

        return null
    }

    fun compute(events: List<Event>): Pair<List<Tribute>, List<Tribute>> {
        val alive = LinkedList<Tribute>()
        val dead = LinkedList<Tribute>()

        events.forEach {
            if (it.action !is HarmfulAction) {
                alive.addAll(it.tributes)
            } else {
                val (aliveTributes, deadTributes) = it.tributes.splitIndexed { i, _ -> !it.action.killed.contains(i) }
                alive.addAll(aliveTributes)
                dead.addAll(deadTributes)
            }
        }

        return Pair(alive, dead)
    }

    private inline fun <T> Iterable<T>.splitIndexed(predicate: (index: Int, T) -> Boolean): Pair<List<T>, List<T>> {
        return splitIndexedTo(ArrayList<T>(), ArrayList<T>(), predicate)
    }

    private inline fun <T, C : MutableCollection<in T>> Iterable<T>.splitIndexedTo(destination1: C, destination2: C, predicate: (index: Int, T) -> Boolean): Pair<C, C> {
        forEachIndexed { index, element ->
            (if (predicate(index, element)) destination1 else destination2).add(element)
        }
        return Pair(destination1, destination2)
    }

}
