package domemod

import basemod.BaseMod
import basemod.ModPanel
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Texture
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import domemod.moves.MonsterDatabase
import org.apache.logging.log4j.LogManager

@SpireInitializer
class DomeMod: PostInitializeSubscriber {
    companion object {
        @JvmStatic
        fun initialize() {
            DomeMod()
        }

        @JvmStatic
        val logger = LogManager.getLogger(DomeMod::class.java.name)
    }

    init {
        BaseMod.subscribe(this)
    }

    override fun receivePostInitialize() {
        MonsterDatabase.load()

        BaseMod.registerModBadge(Texture("domemod/icon.png"), "Dome Visualised", "Jerry",
                "Slay the Spire mod that displays all possible moves of all enemies while in combat when " +
                        "you have Runic Dome. ", ModPanel())
    }
}