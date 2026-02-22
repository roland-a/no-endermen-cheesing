package roland_a.mc_mods.no_endermen_cheesing

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import roland_a.mc_mods.common.config.ConfigLoaderBuilder.Companion.withDefaultSerialization
import roland_a.mc_mods.common.config.ConfigLoaderBuilder.Companion.withDefaultConfigLocation
import roland_a.mc_mods.common.config.ConfigLoaderBuilder.Companion.withLogger
import roland_a.mc_mods.common.config.loadConfig
import roland_a.mc_mods.no_endermen_cheesing.config.Config

@Suppress("MemberVisibilityCanBePrivate")
object Mod: ModInitializer {
	val MOD_ID: String = Mod::class.java.packageName.split(".").last()

	val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	@JvmStatic
	val config: Config by lazy {
		loadConfig {
			withDefaultConfigLocation(MOD_ID)

			withLogger(LOGGER)

			withDefaultSerialization()
		}
	}

	override fun onInitialize() {
		// Ensures that config is always initialized by this point
		config
	}
}
