package pw.aru.hg.engine

import pw.aru.hg.engine.events.EventFormatter
import pw.aru.hg.engine.game.HarmfulAction
import pw.aru.hg.engine.game.HarmlessAction
import pw.aru.hg.engine.game.Phase
import pw.aru.hg.engine.loader.loadFile
import pw.aru.hg.engine.loader.parseHarmfulActions
import pw.aru.hg.engine.loader.parseHarmlessActions
import pw.aru.hg.engine.phases.*
import java.io.File

fun main(args: Array<String>) {
    val hungerGames: pw.aru.hg.engine.HungerGames = pw.aru.hg.engine.HungerGamesBuilder()
        .bloodbathActions(
            harmlessActions("game/events/bloodbath_harmless.txt"),
            harmfulActions("game/events/bloodbath_harmful.txt")
        )
        .dayActions(
            harmlessActions("game/events/day_harmless.txt"),
            harmfulActions("game/events/day_harmful.txt")
        )
        .nightActions(
            harmlessActions("game/events/night_harmless.txt"),
            harmfulActions("game/events/night_harmful.txt")
        )
        .feastActions(
            harmlessActions("game/events/feast_harmless.txt"),
            harmfulActions("game/events/feast_harmful.txt")
        )
        .addTributes(
            *((0 until 24).map { "Tribute $it" }.toTypedArray())
        )
        .threshold(0.9)
        .build()

    val formatter: EventFormatter = EventFormatter {
        "${it.name}${if (it.kills == 0) "" else if (it.kills == 1) " (1 kill)" else " (${it.kills} kills)"}"
    }

    var time = -System.currentTimeMillis()

    for (e: Phase in hungerGames.newGame()) {
        when (e) {
            is Bloodbath -> {
                println("=-=- The Bloodbath -=-=")
                for (event in e.events) {
                    println(event.format(formatter))
                }
                println()
            }
            is Day -> {
                println("=-=- Day ${e.number} -=-=")

                for (event in e.events) {
                    println(event.format(formatter))
                }
                println()
            }
            is FallenTributes -> {
                println("=-=- Fallen Tributes -=-=")
                val fallenTributes = e.fallenTributes

                println("${fallenTributes.size} cannon shots can be heard in the distance.")

                for (tribute in fallenTributes) {
                    println("X ${tribute.format(formatter)}")
                }

                println()
            }
            is Night -> {
                println("=-=- Night ${e.number} -=-=")

                for (event in e.events) {
                    println(event.format(formatter))
                }
                println()
            }
            is Feast -> {
                println("=-=- Feast (Day ${e.number}) -=-=")

                for (event in e.events) {
                    println(formatter.format(event))
                }
                println()
            }
            is Winner -> {
                println("=-=- Winner! -=-=")
                println(formatter.format("{0} is the winner!", listOf(e.winner)))
                println()
            }
            is Draw -> {
                println("=-=- Draw! -=-=")
                println("Everyone is dead. No winners.")
                println()
            }
        }
    }

    time += System.currentTimeMillis()

    println("[Debug] Took $time ms.")
}

fun harmlessActions(file: String): List<HarmlessAction> = parseHarmlessActions(loadFile(File(file)))
fun harmfulActions(file: String): List<HarmfulAction> = parseHarmfulActions(loadFile(File(file)))

