package domemod.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

@SpirePatch(clz = AbstractMonster::class, method = "applyPowers")
class MonsterApplyPowersPatch {
    companion object {
        @JvmStatic
        @SpirePostfixPatch
        fun updateIntents(monster: AbstractMonster) {
            AbstractDungeon.actionManager.addToBottom(object: AbstractGameAction() {
                override fun update() {
                    monster.moveSet.updateIntents(monster)
                    isDone = true
                }
            })
        }
    }
}