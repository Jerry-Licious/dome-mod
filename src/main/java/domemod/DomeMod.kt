package domemod

import basemod.BaseMod
import basemod.interfaces.PostInitializeSubscriber
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
    }
}