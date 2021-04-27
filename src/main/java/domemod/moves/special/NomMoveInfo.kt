package domemod.moves.special

import basemod.ReflectionHacks
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.beyond.Maw
import domemod.moves.MoveInfo

class NomMoveInfo: MoveInfo(AbstractMonster.Intent.ATTACK, 5, 5, 0) {
    override fun updateIntent(monster: AbstractMonster) {
        super.updateIntent(monster)
        if (monster is Maw) {
            multiplier = ReflectionHacks.getPrivate<Int>(monster, Maw::class.java, "turnCount") / 2
            if (multiplier > 1) {
                isMultidamage = true
            }
        }
    }

    override fun clone(): MoveInfo = NomMoveInfo()
}