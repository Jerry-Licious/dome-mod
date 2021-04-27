package domemod.moves.special

import com.megacrit.cardcrawl.monsters.AbstractMonster
import domemod.moves.MoveInfo

class DividerMoveInfo: MoveInfo(AbstractMonster.Intent.ATTACK, 1, 0, 6) {
    override fun updateIntent(monster: AbstractMonster) {
        damage = calculateDamage(monster.damage[2].base, monster)

        intentImage = getIntentImg()
    }

    override fun clone(): MoveInfo = DividerMoveInfo()
}