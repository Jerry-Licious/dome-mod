package domemod.moves

import com.google.gson.annotations.SerializedName

class MoveCollection(@SerializedName("move_sets") val moveSets: ArrayList<AscensionMoveSet>) {
    fun getMoveSet(ascension: Int): AscensionMoveSet = moveSets.filter { ascension >= it.minAscension }.maxOrNull()!!
}