package xyz.cuteclouds.hunger.game

abstract class Action {
    abstract val amount: Int
    abstract val action: String
    abstract val effects: Map<Int, Effect>
    abstract val requires: Map<Int, List<String>>
}