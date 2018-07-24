package pw.aru.hungergames;

import pw.aru.hungergames.data.SimpleTribute;
import pw.aru.hungergames.game.Actions;
import pw.aru.hungergames.game.HarmfulAction;
import pw.aru.hungergames.game.HarmlessAction;
import pw.aru.hungergames.game.Tribute;

import java.util.*;
import java.util.stream.Collectors;

public class HungerGamesBuilder {
    private List<HarmfulAction> bloodbathHarmfulActions;
    private List<HarmlessAction> bloodbathHarmlessActions;

    private List<HarmfulAction> dayHarmfulActions;
    private List<HarmlessAction> dayHarmlessActions;

    private List<HarmfulAction> feastHarmfulActions;
    private List<HarmlessAction> feastHarmlessActions;

    private List<HarmfulAction> nightHarmfulActions;
    private List<HarmlessAction> nightHarmlessActions;

    private Random random;
    private double threshold = 0.7;
    private List<Tribute> tributes = new LinkedList<>();

    public HungerGamesBuilder actions(Actions actions) {
        this.bloodbathHarmfulActions = actions.bloodbathHarmful;
        this.bloodbathHarmlessActions = actions.bloodbathHarmless;
        this.dayHarmfulActions = actions.dayHarmful;
        this.dayHarmlessActions = actions.dayHarmless;
        this.feastHarmfulActions = actions.feastHarmful;
        this.feastHarmlessActions = actions.feastHarmless;
        this.nightHarmfulActions = actions.nightHarmful;
        this.nightHarmlessActions = actions.nightHarmless;
        return this;
    }

    public HungerGamesBuilder addTributes(List<Tribute> tributes) {
        this.tributes.addAll(Objects.requireNonNull(tributes));
        return this;
    }

    public HungerGamesBuilder addTributes(String... tributes) {
        this.tributes.addAll(Arrays.stream(tributes).map(SimpleTribute::new).collect(Collectors.toList()));
        return this;
    }

    public HungerGamesBuilder bloodbathActions(List<HarmlessAction> bloodbathHarmlessActions, List<HarmfulAction> bloodbathHarmfulActions) {
        this.bloodbathHarmfulActions = bloodbathHarmfulActions;
        this.bloodbathHarmlessActions = bloodbathHarmlessActions;
        return this;
    }

    public HungerGamesBuilder bloodbathHarmfulActions(List<HarmfulAction> bloodbathHarmfulActions) {
        this.bloodbathHarmfulActions = bloodbathHarmfulActions;
        return this;
    }

    public HungerGamesBuilder bloodbathHarmlessActions(List<HarmlessAction> bloodbathHarmlessActions) {
        this.bloodbathHarmlessActions = bloodbathHarmlessActions;
        return this;
    }

    public HungerGames build() {
        return new HungerGames(
            tributes,
            new Actions(
                Objects.requireNonNull(bloodbathHarmlessActions, "bloodbathHarmlessActions"),
                Objects.requireNonNull(bloodbathHarmfulActions, "bloodbathHarmfulActions"),
                Objects.requireNonNull(dayHarmlessActions, "dayHarmlessActions"),
                Objects.requireNonNull(dayHarmfulActions, "dayHarmfulActions"),
                Objects.requireNonNull(nightHarmlessActions, "nightHarmlessActions"),
                Objects.requireNonNull(nightHarmfulActions, "nightHarmfulActions"),
                Objects.requireNonNull(feastHarmlessActions, "feastHarmlessActions"),
                Objects.requireNonNull(feastHarmfulActions, "feastHarmfulActions")
            ),
            threshold,
            random == null ? new Random() : random
        );
    }

    public HungerGamesBuilder dayActions(List<HarmlessAction> dayHarmlessActions, List<HarmfulAction> dayHarmfulActions) {
        this.dayHarmfulActions = dayHarmfulActions;
        this.dayHarmlessActions = dayHarmlessActions;
        return this;
    }

    public HungerGamesBuilder dayHarmfulActions(List<HarmfulAction> dayHarmfulActions) {
        this.dayHarmfulActions = dayHarmfulActions;
        return this;
    }

    public HungerGamesBuilder dayHarmlessActions(List<HarmlessAction> dayHarmlessActions) {
        this.dayHarmlessActions = dayHarmlessActions;
        return this;
    }

    public HungerGamesBuilder feastActions(List<HarmlessAction> feastHarmlessActions, List<HarmfulAction> feastHarmfulActions) {
        this.feastHarmfulActions = feastHarmfulActions;
        this.feastHarmlessActions = feastHarmlessActions;
        return this;
    }

    public HungerGamesBuilder feastHarmfulActions(List<HarmfulAction> feastHarmfulActions) {
        this.feastHarmfulActions = feastHarmfulActions;
        return this;
    }

    public HungerGamesBuilder feastHarmlessActions(List<HarmlessAction> feastHarmlessActions) {
        this.feastHarmlessActions = feastHarmlessActions;
        return this;
    }

    public HungerGamesBuilder nightActions(List<HarmlessAction> nightHarmlessActions, List<HarmfulAction> nightHarmfulActions) {
        this.nightHarmfulActions = nightHarmfulActions;
        this.nightHarmlessActions = nightHarmlessActions;
        return this;
    }

    public HungerGamesBuilder nightHarmfulActions(List<HarmfulAction> nightHarmfulActions) {
        this.nightHarmfulActions = nightHarmfulActions;
        return this;
    }

    public HungerGamesBuilder nightHarmlessActions(List<HarmlessAction> nightHarmlessActions) {
        this.nightHarmlessActions = nightHarmlessActions;
        return this;
    }

    public HungerGamesBuilder random(Random random) {
        this.random = random;
        return this;
    }

    public HungerGamesBuilder threshold(double threshold) {
        this.threshold = threshold;
        return this;
    }

    public HungerGamesBuilder tributes(List<Tribute> tributes) {
        this.tributes = Objects.requireNonNull(tributes);
        return this;
    }
}
