package domemod.effects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.vfx.AbstractGameEffect

// Clone of the original FlashIntentParticle class, used to display at a set location instead of above the monster.
class FlashIntentParticleClone(val image: Texture, cX: Float, cY: Float): AbstractGameEffect() {
    companion object {
        @JvmField
        val DURATION = 1f
        @JvmField
        val START_SCALE = 5f * Settings.scale
    }

    val x = cX - image.width / 2
    val y = cY - image.width / 2

    var shineColor = Color(1f, 1f, 1f, 0f)

    init {
        duration = DURATION
        renderBehind = true

        scale = 0.01f
    }

    override fun update() {
        scale = Interpolation.fade.apply(START_SCALE, 0.01f, duration)
        duration -= Gdx.graphics.deltaTime
        if (duration < 0f) isDone = true
    }

    override fun render(spriteBatch: SpriteBatch) {
        spriteBatch.setBlendFunction(770, 1)
        shineColor.a = duration / 2f
        spriteBatch.setColor(shineColor)
        spriteBatch.draw(image, x, y, image.width / 2f, image.width / 2f,
            image.width.toFloat(), image.width.toFloat(), scale, scale, 0f, 0, 0,
            image.width, image.width, false, false)
        spriteBatch.setBlendFunction(770, 771)
    }

    override fun dispose() {}
}