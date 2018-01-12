package xyz.cuteclouds.hunger.game

import xyz.cuteclouds.hunger.events.EventFormatter

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