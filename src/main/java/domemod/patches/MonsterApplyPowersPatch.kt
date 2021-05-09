package domemod.patches

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.AbstractGameEffect

@SpirePatch(clz = AbstractMonster::class, method = "applyPowers")
class MonsterApplyPowersPatch {
    companion object {
        @JvmStatic
        @SpirePostfixPatch
        fun updateIntents(monster: AbstractMonster) {
            AbstractDungeon.effectsQueue.add(object: AbstractGameEffect() {
                override fun dispose() {}
                override fun render(sb: SpriteBatch?) {}

                override fun update() {
                    monster.moveSet.updateIntents(monster)
                    isDone = true
                }
            })
        }
    }
}