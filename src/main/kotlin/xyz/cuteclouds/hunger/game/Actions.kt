package xyz.cuteclouds.hunger.game

data class Actions(
    @JvmField val bloodbathHarmless: List<HarmlessAction>,
    @JvmField val bloodbathHarmful: List<HarmfulAction>,

    @JvmField val dayHarmless: List<HarmlessAction>,
    @JvmField val dayHarmful: List<HarmfulAction>,

    @JvmField val nightHarmless: List<HarmlessAction>,
    @JvmField val nightHarmful: List<HarmfulAction>,

    @JvmField val feastHarmless: List<HarmlessAction>,
    @JvmField val feastHarmful: List<HarmfulAction>
)