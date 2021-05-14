package domemod.moves.special

import com.megacrit.cardcrawl.actions.GameActionManager
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import domemod.moves.MoveInfo

class MultiStabMoveInfo(baseDamage: Int): MoveInfo(AbstractMonster.Intent.ATTACK, 1, baseDamage, 2) {
    override fun updateIntent(monster: AbstractMonster) {
        super.updateIntent(monster)
        if (AbstractDungeon.ascensionLevel >= 18){
            // If it is the enemies' turn, the move is queued before the player's turn starts, so an offset of 1 is added.
            multiplier = if (AbstractDungeon.actionManager.turnHasEnded) { GameActionManager.turn + 2 }
                else { GameActionManager.turn + 1 }
        } else {
            multiplier = (monster.moveHistory.reversed().stream().skip(1)
                    .filter { it == (1).toByte() }.count() + 2).toInt()
        }
    }

    override fun clone(): MoveInfo = MultiStabMoveInfo(baseDamage)
}