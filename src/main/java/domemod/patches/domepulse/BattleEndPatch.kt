package domemod.patches.domepulse

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.relics.RunicDome

@SpirePatch(clz = AbstractPlayer::class, method = "onVictory")
class BattleEndPatch {
    companion object {
        @JvmStatic
        @SpirePostfixPatch
        fun toggleDomePulse(player: AbstractPlayer) {
            if (player.hasRelic(RunicDome.ID)) {
                player.getRelic(RunicDome.ID).stopPulse()
            }
        }
    }
}