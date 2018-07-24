package pw.aru.hungergames.loader

import pw.aru.hungergames.data.SimpleTribute
import pw.aru.hungergames.game.Effect
import pw.aru.hungergames.game.HarmfulAction
import pw.aru.hungergames.game.HarmlessAction
import pw.aru.hungergames.game.Tribute
import pw.aru.hungergames.tuples.ParserOptions.NO_IMPLICIT_TUPLES
import pw.aru.hungergames.tuples.ParserOptions.NO_SMART_TUPLES
import pw.aru.hungergames.tuples.TupleParser
import pw.aru.hungergames.tuples.data.Obj
import pw.aru.hungergames.tuples.data.Pair
import pw.aru.hungergames.tuples.data.Text
import pw.aru.hungergames.tuples.data.Tuple
import java.io.File

fun loadFile(file: File): List<String> {
    return file.readLines()
        .filter { (it.isEmpty() || it.startsWith("#")).not() }
}

fun parseTributes(input: List<String>): List<Tribute> {
    return input.map { TupleParser(it, NO_SMART_TUPLES or NO_IMPLICIT_TUPLES).parse() }
        .map {
            SimpleTribute(
                it["name"].asText()
            )
        }
        .toList()
}

fun parseHarmlessActions(input: List<String>): List<HarmlessAction> {
    return input.map { TupleParser(it, NO_SMART_TUPLES or NO_IMPLICIT_TUPLES).parse() }
        .map {
            HarmlessAction(
                it["amount"].asText().toInt(),
                it["action"].asText(),
                it["effects"].parseEffects(),
                it["required"].parseRequired()
            )
        }
        .toList()
}

fun parseHarmfulActions(input: List<String>): List<HarmfulAction> {
    return input.map { TupleParser(it, NO_SMART_TUPLES or NO_IMPLICIT_TUPLES).parse() }
        .map {
            HarmfulAction(
                it["amount"].asText().toInt(),
                it["action"].asText(),
                it["killed"].asTuple().map { it.asText().substring(1).toInt() },
                it["killers"].asTuple().map { it.asText().substring(1).toInt() },
                it["effects"].parseEffects(),
                it["required"].parseRequired()
            )
        }
        .toList()
}

private fun Obj?.parseRequired(): Map<Int, List<String>> {
    if (this == null || this !is Tuple) return emptyMap()

    return this.mapNotNull { it as? Pair }
        .mapNotNull {
            val (k, v) = it

            if (v is Tuple) Pair(
                k.substring(1).toInt(),
                v.mapNotNull { (it as? Text)?.value }
            ) else null
        }
        .toMap()
}

private fun Obj?.parseEffects(): Map<Int, Effect> {
    if (this == null || this !is Tuple) return emptyMap()

    return this.mapNotNull { it as? Pair }
        .mapNotNull {
            val (k, v) = it

            if (v is Tuple) Pair(
                k.substring(1).toInt(),
                Effect(
                    v["add"]?.asTuple()?.mapNotNull { (it as? Text)?.value } ?: emptyList(),
                    v["remove"]?.asTuple()?.mapNotNull { (it as? Text)?.value } ?: emptyList()
                )
            ) else null
        }
        .toMap()
}

private inline val Text.value
    get() = value()