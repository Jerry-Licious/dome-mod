package domemod.moves

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.megacrit.cardcrawl.monsters.beyond.Transient
import domemod.DomeMod
import domemod.moves.special.SpecialMonsterMoveCollections
import java.io.InputStreamReader

object MonsterDatabase {
    private val gson = Gson()
    private val mapType = object : TypeToken<HashMap<String, MoveCollection>>() {}.type

    @JvmField
    var moveCollections: HashMap<String, MoveCollection> = hashMapOf()
    @JvmStatic
    fun load() {
        moveCollections = gson.fromJson(InputStreamReader(
            MonsterDatabase::class.java.getResourceAsStream("/domemod/monsters.json")), mapType)

        moveCollections["FuzzyLouseNormal"] = SpecialMonsterMoveCollections.fuzzyLouseNormal
        moveCollections["FuzzyLouseDefensive"] = SpecialMonsterMoveCollections.fuzzyLouseDefensive

        moveCollections["Hexaghost"] = SpecialMonsterMoveCollections.hexaghost
        moveCollections["BookOfStabbing"] = SpecialMonsterMoveCollections.bookOfStabbing
        moveCollections["Maw"] = SpecialMonsterMoveCollections.maw
        moveCollections["Darkling"] = SpecialMonsterMoveCollections.darkling
        moveCollections["GiantHead"] = SpecialMonsterMoveCollections.giantHead
        moveCollections["Transient"] = SpecialMonsterMoveCollections.transient
    }

    fun hasMoveSet(monsterID: String) = moveCollections.containsKey(monsterID)
    fun getMoveSet(monsterID: String, ascension: Int) = moveCollections[monsterID]!!.getMoveSet(ascension).clone()
}