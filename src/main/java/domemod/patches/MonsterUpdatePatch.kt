package domemod.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.monsters.AbstractMonster

@SpirePatch(clz = AbstractMonster::class, method = "updateIntent")
class MonsterUpdatePatch {
    companion object {
        @JvmStatic
        @SpirePostfixPatch
        fun updateMoveSet(monster: AbstractMonster) {
            monster.moveSet.update(monster)
        }
    }
}