package domemod.effects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.vfx.AbstractGameEffect

// Clone of the original FlashIntentEffect class, used to display at a set location instead of above the monster.
class FlashIntentEffectClone(val image: Texture?, val cX: Float, val cY: Float): AbstractGameEffect() {
    companion object {
        @JvmField
        val DURATION = 1f
        @JvmField
        val FLASH_INTERVAL = 0.17f
    }

    var intervalTimer = 0f

    init {
        duration = DURATION
    }

    override fun update() {
        intervalTimer -= Gdx.graphics.deltaTime
        if (intervalTimer < 0f) {
            intervalTimer = FLASH_INTERVAL
            if (image != null) {
                AbstractDungeon.effectsQueue.add(FlashIntentParticleClone(image, cX, cY))
            }
        }
        duration -= Gdx.graphics.deltaTime
        if (duration < 0f) isDone = true
    }
    override fun render(spriteBatch: SpriteBatch) {}

    override fun dispose() {}
}