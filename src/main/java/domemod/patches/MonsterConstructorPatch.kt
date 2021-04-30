package domemod.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import domemod.DomeMod
import domemod.moves.AscensionMoveSet
import domemod.moves.MonsterDatabase
import domemod.moves.MoveInfo

@SpirePatch(clz = AbstractMonster::class, method = SpirePatch.CONSTRUCTOR, paramtypez = [
    String::class, String::class, Int::class, Float::class, Float::class, Float::class, Float::class, String::class,
    Float::class, Float::class, Boolean::class
])
class MonsterConstructorPatch {
    companion object {
        @JvmStatic
        @SpirePostfixPatch
        fun initialiseMoveSet(monster: AbstractMonster) {
            val moveSet = if (MonsterDatabase.hasMoveSet(monster.id)) {
                MonsterDatabase.getMoveSet(monster.id, AbstractDungeon.ascensionLevel)
            } else {
                AscensionMoveSet(0, arrayListOf())
            }

            moveSet.updatePositions(monster.intentHb)
            moveSet.updateIntents(monster)

            monster.moveSet = moveSet
        }
    }
}