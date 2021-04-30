package domemod.moves.special

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost
import domemod.moves.MoveInfo

class DividerMoveInfo: MoveInfo(AbstractMonster.Intent.ATTACK, 1, 0, 6) {
    override fun updateIntent(monster: AbstractMonster) {
        if (monster is Hexaghost) {
            damage = calculateDamage(if (monster.nextMove == 5.toByte() || monster.nextMove == (-1).toByte())
                { AbstractDungeon.player.currentHealth / 12 + 1 } else { monster.damage[2].base }, monster)
        }

        intentImage = getIntentImg()
    }

    override fun clone(): MoveInfo = DividerMoveInfo()
}