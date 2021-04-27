package domemod.moves.special

import com.megacrit.cardcrawl.monsters.AbstractMonster
import domemod.moves.AscensionMoveSet
import domemod.moves.MoveCollection
import domemod.moves.MoveInfo

object SpecialMonsterMoveCollections {
    @JvmField
    // Red Louse
    val fuzzyLouseNormal = MoveCollection(arrayListOf(
        AscensionMoveSet(0, arrayListOf(
            RangeMoveInfo(AbstractMonster.Intent.ATTACK, 3, 5, 7, 1),
            MoveInfo(AbstractMonster.Intent.BUFF, 4)
        )),
        AscensionMoveSet(2, arrayListOf(
            RangeMoveInfo(AbstractMonster.Intent.ATTACK, 3, 6, 8, 1),
            MoveInfo(AbstractMonster.Intent.BUFF, 4)
        ))
    ))

    @JvmField
    // Green Louse
    val fuzzyLouseDefensive = MoveCollection(arrayListOf(
        AscensionMoveSet(0, arrayListOf(
            RangeMoveInfo(AbstractMonster.Intent.ATTACK, 3, 5, 7, 1),
            MoveInfo(AbstractMonster.Intent.DEBUFF, 4)
        )),
        AscensionMoveSet(2, arrayListOf(
            RangeMoveInfo(AbstractMonster.Intent.ATTACK, 3, 6, 8, 1),
            MoveInfo(AbstractMonster.Intent.DEBUFF, 4)
        ))
    ))

    @JvmField
    val hexaghost = MoveCollection(arrayListOf(
        AscensionMoveSet(0, arrayListOf(
            MoveInfo(AbstractMonster.Intent.UNKNOWN, 5),
            DividerMoveInfo(),
            MoveInfo(AbstractMonster.Intent.ATTACK_DEBUFF, 4, 6, 1),
            MoveInfo(AbstractMonster.Intent.ATTACK, 2, 5, 2),
            MoveInfo(AbstractMonster.Intent.DEFEND_BUFF, 3),
            MoveInfo(AbstractMonster.Intent.ATTACK_DEBUFF, 6, 2, 6)
        )),
        AscensionMoveSet(4, arrayListOf(
            MoveInfo(AbstractMonster.Intent.UNKNOWN, 5),
            DividerMoveInfo(),
            MoveInfo(AbstractMonster.Intent.ATTACK_DEBUFF, 4, 6, 1),
            MoveInfo(AbstractMonster.Intent.ATTACK, 2, 6, 2),
            MoveInfo(AbstractMonster.Intent.DEFEND_BUFF, 3),
            MoveInfo(AbstractMonster.Intent.ATTACK_DEBUFF, 6, 3, 6)
        ))
    ))

    @JvmField
    val bookOfStabbing = MoveCollection(arrayListOf(
        AscensionMoveSet(0, arrayListOf(
            MultiStabMoveInfo(6),
            MoveInfo(AbstractMonster.Intent.ATTACK, 2, 21, 1)
        )),
        AscensionMoveSet(3, arrayListOf(
            MultiStabMoveInfo(7),
            MoveInfo(AbstractMonster.Intent.ATTACK, 2, 24, 1)
        ))
    ))

    @JvmField
    val maw = MoveCollection(arrayListOf(
        AscensionMoveSet(0, arrayListOf(
            MoveInfo(AbstractMonster.Intent.STRONG_DEBUFF, 2),
            MoveInfo(AbstractMonster.Intent.ATTACK, 3, 25, 1),
            NomMoveInfo()
        )),
        AscensionMoveSet(2, arrayListOf(
            MoveInfo(AbstractMonster.Intent.STRONG_DEBUFF, 2),
            MoveInfo(AbstractMonster.Intent.ATTACK, 3, 30, 1),
            NomMoveInfo()
        ))
    ))

    @JvmField
    val darkling = MoveCollection(arrayListOf(
        AscensionMoveSet(0, arrayListOf(
            RangeMoveInfo(AbstractMonster.Intent.ATTACK, 3, 7, 11, 1),
            MoveInfo(AbstractMonster.Intent.DEFEND, 2),
            MoveInfo(AbstractMonster.Intent.ATTACK, 1, 8, 2),
            MoveInfo(AbstractMonster.Intent.UNKNOWN, 4),
            MoveInfo(AbstractMonster.Intent.BUFF, 5)
        )),
        AscensionMoveSet(2, arrayListOf(
            RangeMoveInfo(AbstractMonster.Intent.ATTACK, 3, 9, 13, 1),
            MoveInfo(AbstractMonster.Intent.DEFEND, 2),
            MoveInfo(AbstractMonster.Intent.ATTACK, 1, 9, 2),
            MoveInfo(AbstractMonster.Intent.UNKNOWN, 4),
            MoveInfo(AbstractMonster.Intent.BUFF, 5)
        )),
        AscensionMoveSet(17, arrayListOf(
            RangeMoveInfo(AbstractMonster.Intent.ATTACK, 3, 9, 13, 1),
            MoveInfo(AbstractMonster.Intent.DEFEND_BUFF, 2),
            MoveInfo(AbstractMonster.Intent.ATTACK, 1, 9, 2),
            MoveInfo(AbstractMonster.Intent.UNKNOWN, 4),
            MoveInfo(AbstractMonster.Intent.BUFF, 5)
        ))
    ))

    val giantHead = MoveCollection(arrayListOf(
        AscensionMoveSet(0, arrayListOf(
            MoveInfo(AbstractMonster.Intent.ATTACK, 3, 13, 1),
            MoveInfo(AbstractMonster.Intent.DEBUFF, 1),
            ItIsTimeMoveInfo(30)
        )),
        AscensionMoveSet(3, arrayListOf(
            MoveInfo(AbstractMonster.Intent.ATTACK, 3, 13, 1),
            MoveInfo(AbstractMonster.Intent.DEBUFF, 1),
            ItIsTimeMoveInfo(40)
        ))
    ))

    val transient = MoveCollection(arrayListOf(
        AscensionMoveSet(0, arrayListOf(
            TransientAttackMoveInfo(30)
        )),
        AscensionMoveSet(2, arrayListOf(
            TransientAttackMoveInfo(40)
        ))
    ))
}