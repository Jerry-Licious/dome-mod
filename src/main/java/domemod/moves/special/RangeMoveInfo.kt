package domemod.moves.special

import com.megacrit.cardcrawl.monsters.AbstractMonster
import domemod.moves.MoveInfo

class RangeMoveInfo(intent: AbstractMonster.Intent, moveID: Byte, minBaseDamage: Int = 0,
                    val maxBaseDamage: Int = 0, multiplier: Int = 0):
    MoveInfo(intent, moveID, minBaseDamage, multiplier) {
    @Transient
    var maxDamage = maxBaseDamage

    override fun updateIntent(monster: AbstractMonster) {
        super.updateIntent(monster)
        maxDamage = calculateDamage(maxBaseDamage, monster)
    }

    override fun getDamageString(): String = if (isMultidamage) "${damage}-${maxDamage}x${multiplier}"
        else { "${damage}-${maxDamage}" }

    override fun clone(): MoveInfo = RangeMoveInfo(intent, moveID, baseDamage, maxBaseDamage, multiplier)
}