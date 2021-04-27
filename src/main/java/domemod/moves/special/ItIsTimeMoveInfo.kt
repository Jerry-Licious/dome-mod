package domemod.moves.special

import com.megacrit.cardcrawl.monsters.AbstractMonster
import domemod.moves.MoveInfo

class ItIsTimeMoveInfo(baseDamage: Int): MoveInfo(AbstractMonster.Intent.ATTACK, 2, baseDamage, 0) {
    override fun updateIntent(monster: AbstractMonster) {
        damage = calculateDamage(baseDamage +
                // Exclude the current move.
                (monster.moveHistory.count { it == moveID } - 1).coerceAtLeast(6) * 5, monster)

        intentImage = getIntentImg()
    }

    override fun clone(): MoveInfo = ItIsTimeMoveInfo(baseDamage)
}