package xyz.cuteclouds.hunger.loader

import xyz.cuteclouds.hunger.data.*
import xyz.cuteclouds.hunger.game.Effect
import xyz.cuteclouds.hunger.game.HarmfulAction
import xyz.cuteclouds.hunger.game.HarmlessAction
import xyz.cuteclouds.hunger.game.Tribute
import xyz.cuteclouds.utils.args.ArgParser
import xyz.cuteclouds.utils.args.ParserOptions.NO_IMPLICIT_TUPLES
import xyz.cuteclouds.utils.args.ParserOptions.NO_SMART_TUPLES
import xyz.cuteclouds.utils.args.tuples.Arg
import xyz.cuteclouds.utils.args.tuples.Pair
import xyz.cuteclouds.utils.args.tuples.Text
import xyz.cuteclouds.utils.args.tuples.Tuple
import java.io.File

fun loadFile(file: File): List<String> {
    return file
        .lines()
        .filter { (it.isEmpty() || it.startsWith("#")).not() }
}

fun parseTributes(input: List<String>): List<Tribute> {
    return input.map { ArgParser(it, NO_SMART_TUPLES or NO_IMPLICIT_TUPLES).parse() }
        .map {
            SimpleTribute(
                it["name"].asText()
            )
        }
        .toList()
}

fun parseHarmlessActions(input: List<String>): List<HarmlessAction> {
    return input.map { ArgParser(it, NO_SMART_TUPLES or NO_IMPLICIT_TUPLES).parse() }
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
    return input.map { ArgParser(it, NO_SMART_TUPLES or NO_IMPLICIT_TUPLES).parse() }
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

private fun Arg?.parseRequired(): Map<Int, List<String>> {
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

private fun Arg?.parseEffects(): Map<Int, Effect> {
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

private val Text.value
    get() = value()
