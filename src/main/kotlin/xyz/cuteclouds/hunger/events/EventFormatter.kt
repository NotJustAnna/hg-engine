package xyz.cuteclouds.hunger.events

import xyz.cuteclouds.hunger.game.Event
import xyz.cuteclouds.hunger.game.Tribute
import java.text.MessageFormat
import java.util.HashMap

class EventFormatter(private val converter: (Tribute) -> String = Tribute::name) {
    private val cached = HashMap<String, MessageFormat>()

    fun format(event: Event): String = format(event.action.action, event.tributes)

    fun format(format: String, tributes: List<Tribute>): String {
        return cached.computeIfAbsent(format.replace("'", "''"), ::MessageFormat)
            .format(tributes.map(converter).toTypedArray())
    }

    fun format(tribute: Tribute) = converter(tribute)

    fun cache(formats: List<String>) {
        for (format in formats) cached.computeIfAbsent(format.replace("'", "''"), ::MessageFormat)
    }
}