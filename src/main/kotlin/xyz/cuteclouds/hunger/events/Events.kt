package xyz.cuteclouds.hunger.events

import xyz.cuteclouds.hunger.data.*
import xyz.cuteclouds.hunger.utils.splitIndexed
import java.text.MessageFormat
import java.util.*

data class Event(
    val action: Action,
    val tributes: List<Tribute>
) {
    fun format(formatter: EventFormatter): String = formatter.format(this)

    companion object {
        fun create(action: Action, tributes: List<Tribute>): Event {
            val tributesWithEffects = tributes
                .mapIndexed { i, tribute ->

                    if (action is HarmfulAction && action.killers.contains(i)) {
                        tribute.kills += action.killed.size
                    }

                    val effect = action.effects.getOrDefault(i, Effect.empty)

                    tribute.attributes.removeAll(effect.remove)
                    tribute.attributes.addAll(effect.add)


                    tribute

                }.toList()

            return Event(action, tributesWithEffects)
        }
    }
}

class EventFormatter(private val converter: (Tribute) -> String = Tribute::name) {
    private val cached = HashMap<String, MessageFormat>()

    fun format(event: Event): String = format(event.action.action, event.tributes)

    fun format(format: String, tributes: List<Tribute>) : String {
        return cached.computeIfAbsent(format.replace("'", "''"), ::MessageFormat)
            .format(tributes.map(converter).toTypedArray())
    }

    fun format(tribute: Tribute) = converter(tribute)

    fun cache(formats: List<String>) {
        for (format in formats) cached.computeIfAbsent(format.replace("'", "''"), ::MessageFormat)
    }
}

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

}