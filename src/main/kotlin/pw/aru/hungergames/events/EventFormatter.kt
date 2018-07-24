package pw.aru.hungergames.events

import pw.aru.hungergames.game.Event
import pw.aru.hungergames.game.Tribute
import java.text.MessageFormat
import java.util.*

class EventFormatter(private val converter: (Tribute) -> String = Tribute::name) {

    fun format(event: Event): String = format(event.action.action, event.tributes)

    fun format(format: String, tributes: List<Tribute>): String {
        return EventFormatter[format].format(tributes.map(converter).toTypedArray())
    }

    fun format(tribute: Tribute) = converter(tribute)

    companion object {
        private val cached = HashMap<String, MessageFormat>()

        operator fun get(format: String) = cached.computeIfAbsent(format.replace("'", "''"), ::MessageFormat)

        fun cache(formats: List<String>) = formats.forEach { get(it) }
    }
}