package domemod.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.RunicDome

@SpirePatch(clz = AbstractMonster::class, method = "flashIntent")
class MonsterFlashIntentPatch {
    companion object {
        @JvmStatic
        @SpirePrefixPatch
        // Prevent the original intent flash and flash from the move set if the player has dome.
        fun flashIntents(monster: AbstractMonster): SpireReturn<Void> {
            if (AbstractDungeon.player.hasRelic(RunicDome.ID)) {
                monster.moveSet.flashIntent(monster.nextMove)
                return SpireReturn.Return(null)
            }
            return SpireReturn.Continue()
        }
    }
}