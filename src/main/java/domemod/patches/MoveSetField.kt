package domemod.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireField
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.monsters.AbstractMonster
import domemod.moves.AscensionMoveSet

@SpirePatch(clz = AbstractMonster::class, method = SpirePatch.CLASS)
class MoveCollectionField {
    companion object {
        @JvmField
        val moveSet: SpireField<AscensionMoveSet> = SpireField { AscensionMoveSet(0, arrayListOf()) }
    }
}

var AbstractMonster.moveSet: AscensionMoveSet
    get() = MoveCollectionField.moveSet.get(this)
    set(value) = MoveCollectionField.moveSet.set(this, value)