package domemod.moves.special

import basemod.ReflectionHacks
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.beyond.Transient
import domemod.DomeMod
import domemod.moves.MoveInfo

class TransientAttackMoveInfo(baseDamage: Int): MoveInfo(AbstractMonster.Intent.ATTACK, 1, baseDamage, 1) {
    override fun updateIntent(monster: AbstractMonster) {
        var additionalDamage = if (monster is Transient) {
            ReflectionHacks.getPrivate<Int>(monster, Transient::class.java, "count") * 10
        } else { 0 }

        damage = calculateDamage(baseDamage + additionalDamage, monster)

        intentImage = getIntentImg()
    }

    override fun clone(): MoveInfo = TransientAttackMoveInfo(baseDamage)
}