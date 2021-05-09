package domemod.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.monsters.AbstractMonster

@SpirePatch(clz = AbstractMonster::class, method = "setMove", paramtypez = [String::class, Byte::class,
    AbstractMonster.Intent::class, Int::class, Int::class, Boolean::class])
class MonsterSetMovePatch {
    companion object {
        @JvmStatic
        @SpirePostfixPatch
        fun updateIntents(monster: AbstractMonster) {
            monster.moveSet.updateIntents(monster)
        }
    }
}