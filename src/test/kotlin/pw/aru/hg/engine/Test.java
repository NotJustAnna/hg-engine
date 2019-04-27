package pw.aru.hg.engine;

import pw.aru.hg.engine.events.EventFormatter;
import pw.aru.hg.engine.game.*;
import pw.aru.hg.engine.phases.*;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static pw.aru.hg.engine.loader.ParsersKt.*;

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
                "AdrianTodt", "Dust", "Slya", "Cherry", "White", "D4rk", "Fabri", "Steven", "Aluca", "Checkium",
                "Esdrico", "GeeLeonidas", "Henry", "LorenzoDCC", "LoneFolf", "Lun", "Lux", "Steffanie", "Gaster", "Darius"
            )
            .threshold(0.9)
            .build();

        EventFormatter formatter = new EventFormatter(
            it -> "**" + it.getName() + "** ``(" + (it.getKills() == 1 ? "1 kill" : it.getKills() + " kills") + ")``");

        long time = -System.currentTimeMillis();

        List<Tribute> fallens = new LinkedList<>();

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

                fallens.addAll(fallenTributes);

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

                println("=-=- Feast (Day " + e.getNumber() + ") -=-=");

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

                println("=-=- Ranking: Survival -=-=");

                int x = fallens.size() + 1;
                String formatString = "%0" + (x <= 9 ? 1 : x <= 99 ? 2 : x <= 999 ? 3 : x <= 9999 ? 4 : 5) + "d - %s";

                println(String.format(formatString, 1, e.getWinner().format(formatter)));

                Collections.reverse(fallens);

                {
                    int i = 1;
                    for (Tribute fallen : fallens) {
                        i++;

                        println(String.format(formatString, i, fallen.format(formatter)));
                    }
                }

                println();

                println("=-=- Ranking: Kills -=-=");

                List<Tribute> killers = new LinkedList<>(e.getGame().getTributes());
                killers.sort(Comparator.comparingInt(Tribute::getKills).reversed());

                {
                    int i = 0;
                    for (Tribute tribute : killers) {
                        i++;

                        println(String.format(formatString, i, tribute.format(formatter)));
                    }
                }

                println();

                continue;
            }

            if (p instanceof Draw) {
                Draw e = (Draw) p;

                println("=-=- Draw! -=-=");
                println("Everyone is dead. No winners.");
                println();

                int x = fallens.size() + 1;
                String formatString = "%0" + (x <= 9 ? 1 : x <= 99 ? 2 : x <= 999 ? 3 : x <= 9999 ? 4 : 5) + "d - %s";

                Collections.reverse(fallens);

                {
                    int i = 0;
                    for (Tribute fallen : fallens) {
                        i++;

                        println(String.format(formatString, i, fallen.format(formatter)));
                    }
                }

                println();

                println("=-=- Ranking: Kills -=-=");

                List<Tribute> killers = new LinkedList<>(e.getGame().getTributes());

                killers.sort(Comparator.comparingInt(Tribute::getKills).reversed());

                {
                    int i = 0;
                    for (Tribute tribute : killers) {
                        i++;

                        println(String.format(formatString, i, tribute.format(formatter)));
                    }
                }

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
