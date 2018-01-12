package xyz.cuteclouds.hunger;

import xyz.cuteclouds.hunger.events.EventFormatter;
import xyz.cuteclouds.hunger.game.*;
import xyz.cuteclouds.hunger.phases.*;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static xyz.cuteclouds.hunger.loader.ParsersKt.*;

public class Test {
    public static void main(String[] args) {
        HungerGames hungerGames = new HungerGamesBuilder()
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
                IntStream.range(0, 24).mapToObj(i -> "Tribute " + i).toArray(String[]::new)
            )
            .threshold(0.7)
            .build();

        EventFormatter formatter = new EventFormatter(
            it -> it.getName() + (it.getKills() == 0 ? "" : it.getKills() == 1 ? " (1 kill)" : " (" + it.getKills() + " kills)")
        );

        long time = -System.currentTimeMillis();

        for (Phase p : hungerGames.newGame()) {
            if (p instanceof Bloodbath) {
                Bloodbath e = (Bloodbath) p;

                println("=-=- The Bloodbath -=-=");
                for (Event event : e.getEvents()) {
                    println(event.format(formatter));
                }
                println();

                continue;
            }

            if (p instanceof Day) {
                Day e = (Day) p;

                println("=-=- Day " + e.getNumber() + " -=-=");

                for (Event event : e.getEvents()) {
                    println(event.format(formatter));
                }

                println();

                continue;
            }

            if (p instanceof FallenTributes) {
                FallenTributes e = (FallenTributes) p;

                println("=-=- Fallen Tributes -=-=");
                List<Tribute> fallenTributes = e.getFallenTributes();

                println(fallenTributes.size() + " cannon shots can be heard in the distance.");

                for (Tribute tribute : fallenTributes) {
                    println("X " + tribute.format(formatter));
                }

                println();

                continue;
            }

            if (p instanceof Night) {
                Night e = (Night) p;

                println("=-=- Night " + e.getNumber() + " -=-=");

                for (Event event : e.getEvents()) {
                    println(event.format(formatter));
                }

                println();

                continue;
            }

            if (p instanceof Feast) {
                Feast e = (Feast) p;

                println("=-=- Feast (Day " + e.getNumber() + " -=-=");

                for (Event event : e.getEvents()) {
                    println(event.format(formatter));
                }

                println();

                continue;
            }

            if (p instanceof Winner) {
                Winner e = (Winner) p;

                println("=-=- Winner! -=-=");
                println(formatter.format("{0} is the winner!", listOf(e.getWinner())));
                println();

                continue;
            }

            if (p instanceof Draw) {
                Draw e = (Draw) p;

                println("=-=- Draw! -=-=");
                println("Everyone is dead. No winners.");
                println();

                continue;
            }
        }

        time += System.currentTimeMillis();

        println("[Debug] Took " + time + " ms.");
    }

    private static List<HarmfulAction> harmfulActions(String file) {
        return parseHarmfulActions(loadFile(new File(file)));
    }

    private static List<HarmlessAction> harmlessActions(String file) {
        return parseHarmlessActions(loadFile(new File(file)));
    }

    private static <T> List<T> listOf(T o) {
        return Collections.singletonList(o);
    }

    private static void println() {
        System.out.println();
    }

    private static void println(String string) {
        System.out.println(string);
    }
}
