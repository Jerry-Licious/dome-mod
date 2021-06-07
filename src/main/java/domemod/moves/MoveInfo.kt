package domemod.moves

import basemod.ReflectionHacks
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.google.gson.annotations.SerializedName
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.Hitbox
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.helpers.input.InputHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.ReactivePower
import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import com.megacrit.cardcrawl.vfx.DebuffParticleEffect
import com.megacrit.cardcrawl.vfx.ShieldParticleEffect
import com.megacrit.cardcrawl.vfx.combat.BuffParticleEffect
import com.megacrit.cardcrawl.vfx.combat.StunStarEffect
import com.megacrit.cardcrawl.vfx.combat.UnknownParticleEffect
import domemod.effects.FlashIntentEffectClone
import kotlin.math.roundToInt

open class MoveInfo(val intent: AbstractMonster.Intent,
                    @SerializedName("move_id") val moveID: Byte,
                    @SerializedName("base_damage") val baseDamage: Int = 0,
                    var multiplier: Int = 0): Cloneable {
    companion object {
        @JvmField
        val enabledForegroundColour = Color.WHITE.cpy()
        @JvmField
        val disabledForegroundColour = Color(1f, 1f, 1f, 0.2f)

        @JvmField
        val defaultBackgroundColour = Color(1f, 1f, 1.0f, 0.4f)
        @JvmField
        val hoveredBackgroundColour = Color(1f, 1f, 1.0f, 0.5f)
        @JvmField
        val lastMoveBackgroundColour = Color(1f, 0.5f, 0.5f, 0.5f)
        @JvmField
        val hoveredLastMoveBackgroundColour = Color(1f, 0.5f, 0.5f, 0.6f)

        @JvmField
        val intentHitboxSize = 64f * Settings.scale
        @JvmField
        val intentSpacingDistance = intentHitboxSize * 1.2f

        @JvmField
        val background = Texture("domemod/intent_background.png")
        @JvmField
        val backgroundSize = 70f
        @JvmField
        val halfBackgroundSize = backgroundSize / 2
    }

    @Transient
    var damage = baseDamage
    @Transient
    var isMultidamage = multiplier > 1

    @Transient
    var hitbox = Hitbox(intentHitboxSize, intentHitboxSize)
    fun setPosition(centerX: Float, centerY: Float) {
        hitbox.x = centerX - intentHitboxSize / 2
        hitbox.y = centerY - intentHitboxSize / 2

        hitbox.cX = centerX
        hitbox.cY = centerY
    }

    @Transient
    val vfx: ArrayList<AbstractGameEffect> = arrayListOf()
    @Transient
    var intentParticleTimer: Float = 0.5f

    @Transient
    var intentImage: Texture? = getIntentImg()

    @Transient
    var enabled = true
    @Transient
    var foregroundColour = enabledForegroundColour.cpy()
    @Transient
    var backgroundColour = defaultBackgroundColour.cpy()

    fun update(monster: AbstractMonster) {
        updateHitbox()
        toggle()
        updateBackgroundColour(monster)

        if (!monster.isDying && !monster.isEscaping) {
            updateVfx()
        }
        vfx.forEach { it.update() }
        vfx.removeAll { it.isDone }
    }

    private fun updateHitbox() {
        hitbox.update()

        if (hitbox.hovered && InputHelper.justClickedLeft) {
            hitbox.clickStarted = true
        }
    }

    private fun toggle() {
        if (hitbox.clicked) {
            hitbox.clicked = false

            enabled = !enabled
            if (enabled) {
                foregroundColour = enabledForegroundColour
            } else {
                foregroundColour = disabledForegroundColour
            }
        }
    }

    private fun updateBackgroundColour(monster: AbstractMonster) {
        backgroundColour = if (
            // Writhing mass actually queues moves into the move history when the reactive buff activates,
            // causing the player to *know* the last move it rolled. An option is added to disable this
            // behaviour.
            !monster.hasPower(ReactivePower.POWER_ID) &&
            // Render the last move with a red background.
            // The last move in the move history is actually the current move. To fetch the last move, access the
            // second last move instead.
            monster.moveHistory.size > 1 &&
            moveID == monster.moveHistory[monster.moveHistory.size - 2]) {
            if (hitbox.hovered) {
                hoveredLastMoveBackgroundColour
            } else {
                lastMoveBackgroundColour
            }
        } else {
            if (hitbox.hovered) {
                hoveredBackgroundColour
            } else {
                defaultBackgroundColour
            }
        }.cpy()

        // If the icon is disabled, make the background more transparent.
        if (!enabled) {
            backgroundColour.a /= 2
        }
    }

    private fun updateVfx() {
        intentParticleTimer -= Gdx.graphics.deltaTime
        when (intent) {
            AbstractMonster.Intent.ATTACK_DEBUFF, AbstractMonster.Intent.DEBUFF,
            AbstractMonster.Intent.STRONG_DEBUFF, AbstractMonster.Intent.DEFEND_DEBUFF -> {
                if (intentParticleTimer < 0f) {
                    intentParticleTimer = 1f
                    vfx.add(DebuffParticleEffect(hitbox.cX, hitbox.cY))
                }
            }
            AbstractMonster.Intent.ATTACK_BUFF, AbstractMonster.Intent.BUFF, AbstractMonster.Intent.DEFEND_BUFF -> {
                if (intentParticleTimer < 0f) {
                    intentParticleTimer = 0.1f
                    vfx.add(BuffParticleEffect(hitbox.cX, hitbox.cY))
                }
            }
            AbstractMonster.Intent.ATTACK_DEFEND -> {
                if (intentParticleTimer < 0f) {
                    intentParticleTimer = 0.5f
                    vfx.add(ShieldParticleEffect(hitbox.cX, hitbox.cY))
                }
            }
            AbstractMonster.Intent.UNKNOWN -> {
                if (intentParticleTimer < 0f) {
                    intentParticleTimer = 0.5f
                    vfx.add(UnknownParticleEffect(hitbox.cX, hitbox.cY))
                }
            }
            AbstractMonster.Intent.STUN -> {
                if (intentParticleTimer < 0f) {
                    intentParticleTimer = 0.67f
                    vfx.add(StunStarEffect(hitbox.cX, hitbox.cY))
                }
            }
        }
    }

    fun render(spriteBatch: SpriteBatch) {
        renderBackground(spriteBatch)

        // Render vfx behind
        if (enabled) vfx.forEach { if (it.renderBehind) it.render(spriteBatch) }
        renderIntent(spriteBatch)
        // Render vfx after
        if (enabled) vfx.forEach { if (!it.renderBehind) it.render(spriteBatch) }
        renderDamageRange(spriteBatch)

        hitbox.render(spriteBatch)
    }

    fun renderBackground(spriteBatch: SpriteBatch) {
        spriteBatch.setColor(backgroundColour)
        spriteBatch.draw(background, hitbox.cX - halfBackgroundSize, hitbox.cY - halfBackgroundSize,
                halfBackgroundSize, halfBackgroundSize, backgroundSize, backgroundSize, Settings.scale, Settings.scale, 0f,
            0, 0, 200, 200, false, false)
    }

    fun renderIntent(spriteBatch: SpriteBatch) {
        spriteBatch.setColor(foregroundColour)
        if (intentImage != null) {
            spriteBatch.draw(intentImage, hitbox.cX - 64f, hitbox.cY - 64f, 64f, 64f,
                128f, 128f, Settings.scale, Settings.scale, 0f,
                0, 0, 128, 128, false, false)
        }
    }

    fun renderDamageRange(spriteBatch: SpriteBatch) {
        if (intent.name.contains("ATTACK")) {
            FontHelper.renderFontLeftTopAligned(spriteBatch, FontHelper.topPanelInfoFont, getDamageString(),
                hitbox.cX - 30f * Settings.scale, hitbox.cY - 12f * Settings.scale, foregroundColour)
        }
    }

    open fun getDamageString() = if (isMultidamage) { "${damage}x${multiplier}" } else { damage.toString() }

    open fun updateIntent(monster: AbstractMonster) {
        damage = calculateDamage(baseDamage, monster)

        intentImage = getIntentImg()
    }

    fun getIntentImg(): Texture? {
        return when (intent) {
            AbstractMonster.Intent.ATTACK, AbstractMonster.Intent.ATTACK_BUFF,
            AbstractMonster.Intent.ATTACK_DEBUFF, AbstractMonster.Intent.ATTACK_DEFEND -> getAttackImg()
            AbstractMonster.Intent.BUFF -> ImageMaster.INTENT_BUFF_L
            AbstractMonster.Intent.DEBUFF -> ImageMaster.INTENT_DEBUFF_L
            AbstractMonster.Intent.STRONG_DEBUFF -> ImageMaster.INTENT_DEBUFF2_L
            AbstractMonster.Intent.DEFEND, AbstractMonster.Intent.DEFEND_DEBUFF -> ImageMaster.INTENT_DEFEND_L
            AbstractMonster.Intent.DEFEND_BUFF -> ImageMaster.INTENT_DEFEND_BUFF_L
            AbstractMonster.Intent.ESCAPE -> ImageMaster.INTENT_ESCAPE_L
            AbstractMonster.Intent.MAGIC -> ImageMaster.INTENT_MAGIC_L
            AbstractMonster.Intent.SLEEP -> ImageMaster.INTENT_SLEEP_L
            AbstractMonster.Intent.STUN -> null
            else -> ImageMaster.INTENT_UNKNOWN_L
        }
    }

    private fun getAttackImg(): Texture {
        val totalDamage = if (isMultidamage) { damage * multiplier } else { damage }

        if (totalDamage < 5) { return ImageMaster.INTENT_ATK_1 }
        if (totalDamage < 10) { return ImageMaster.INTENT_ATK_2 }
        if (totalDamage < 15) { return ImageMaster.INTENT_ATK_3 }
        if (totalDamage < 20) { return ImageMaster.INTENT_ATK_4 }
        if (totalDamage < 25) { return ImageMaster.INTENT_ATK_5 }
        if (totalDamage < 30) { return ImageMaster.INTENT_ATK_6 }

        return ImageMaster.INTENT_ATK_7
    }

    fun calculateDamage(base: Int, monster: AbstractMonster): Int {
        val player = AbstractDungeon.player
        var tmp: Float = base.toFloat()
        if (Settings.isEndless && player.hasBlight("DeadlyEnemies")) {
            tmp *= player.getBlight("DeadlyEnemies").effectFloat()
        }

        monster.powers.forEach { tmp = it.atDamageGive(tmp, DamageInfo.DamageType.NORMAL) }
        player.powers.forEach { tmp = it.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL) }

        tmp = player.stance.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL)

        if (ReflectionHacks.privateMethod(AbstractMonster::class.java, "applyBackAttack").invoke(monster)) {
            tmp = (tmp * 1.5f).toInt().toFloat()
        }

        monster.powers.forEach { tmp = it.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL) }
        player.powers.forEach { tmp = it.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL) }

        return tmp.toInt().coerceAtLeast(0)
    }

    fun flashIntent(move: Byte) {
        if (moveID == move) {
            AbstractDungeon.effectsQueue.add(FlashIntentEffectClone(intentImage, hitbox.cX, hitbox.cY))
        }
    }

    public override fun clone() = MoveInfo(intent, moveID, baseDamage, multiplier)
}