# HungerGames
A Hunger Games Simulator Engine made in **Kotlin**.


## How to use

1. Create an instance of `pw.aru.hungergames.HungerGamesBuilder` and configurate the match.

```kotlin
val hungerGames: HungerGames = pw.aru.hungergames.HungerGamesBuilder()
    ...
    .build()
```

Look at ``src/test/kotlin/pw/aru/hungergames/Test.kt`` for a better example.

2. Start a new match.

```kotlin
val initialPhase: Phase = hungerGames.newGame()
```

2.1. You can iterate over the Phases at any time.

```kotlin
for (e: Phase in initialPhase) {
    ...
}
```

3. Process the events generated.

```kotlin
when (phase) {
    //Game Start Event
    is Bloodbath -> { ... }
    
    //Regular Events
    is Day -> { ... }
    is FallenTributes -> { ... }
    is Night -> { ... }
    is Feast -> { ... }
    
    //Game End Events
    is Winner -> { ... }
    is Draw -> { ... }
}
```