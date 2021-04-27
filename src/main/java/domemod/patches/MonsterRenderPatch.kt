package domemod.patches

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.RunicDome
import com.megacrit.cardcrawl.rooms.AbstractRoom

@SpirePatch(clz = AbstractMonster::class, method = "render")
class MonsterRenderPatch {
    companion object {
        @JvmStatic
        @SpirePostfixPatch
        fun renderMoveSet(monster: AbstractMonster, spriteBatch: SpriteBatch) {
            // Replicate original render conditions for intents except for runic dome.
            if (!monster.isDeadOrEscaped && !monster.isDying && !monster.isEscaping &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                !AbstractDungeon.player.isDead && !Settings.hideCombatElements &&
                AbstractDungeon.player.hasRelic(RunicDome.ID)) {
                monster.moveSet.render(spriteBatch)
            }
        }
    }
}