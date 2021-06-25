package domemod.moves

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.google.gson.annotations.SerializedName
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.Hitbox
import com.megacrit.cardcrawl.monsters.AbstractMonster
import domemod.DomeMod
import domemod.moves.special.TransientAttackMoveInfo
import kotlin.collections.ArrayList

// The move collection stores a list of possible moves for a specific monster.
class AscensionMoveSet(@SerializedName("min_ascension") val minAscension: Int,
                       val moves: ArrayList<MoveInfo>,
                       @Transient
                       val lastMove: Int = -1):
    Comparable<AscensionMoveSet>, Cloneable {
    fun update(monster: AbstractMonster) {
        moves.forEach { it.update(monster) }
    }
    // Position all the moves based on the original position of the enemy's intent.
    fun updatePositions(originalIntentHitbox: Hitbox) {
        val totalHeight = MoveInfo.intentSpacingDistance * (moves.size + 0.5f)
        // Make sure that the top panel does not block the move set.
        val offset = if (originalIntentHitbox.cY + totalHeight > Settings.HEIGHT - 64f * Settings.scale) {
            originalIntentHitbox.cY + totalHeight - (Settings.HEIGHT - 64f * Settings.scale)
        } else {
            0f
        }
        moves.forEachIndexed { index, moveInfo ->
            moveInfo.setPosition(originalIntentHitbox.cX,
                originalIntentHitbox.cY - offset + index * MoveInfo.intentSpacingDistance)
        }
    }

    fun updateIntents(monster: AbstractMonster) {
        moves.forEach { it.updateIntent(monster) }
    }

    fun render(spriteBatch: SpriteBatch) {
        moves.forEach { it.render(spriteBatch) }
    }

    fun flashIntent(move: Byte) {
        moves.forEach { it.flashIntent(move) }
    }

    override fun compareTo(other: AscensionMoveSet) = minAscension.compareTo(other.minAscension)

    public override fun clone() = AscensionMoveSet(minAscension, moves.map { it.clone() }.toCollection(ArrayList()))
}