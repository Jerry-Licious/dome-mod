package domemod.moves.special

import basemod.ReflectionHacks
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing
import domemod.moves.MoveInfo

class MultiStabMoveInfo(baseDamage: Int): MoveInfo(AbstractMonster.Intent.ATTACK, 1, baseDamage, 2) {
    override fun updateIntent(monster: AbstractMonster) {
        super.updateIntent(monster)
        if (monster is BookOfStabbing) {
            multiplier = ReflectionHacks.getPrivate(monster, BookOfStabbing::class.java, "stabCount")
        }
    }

    override fun clone(): MoveInfo = MultiStabMoveInfo(baseDamage)
}