package roland_a.mc_mods.no_endermen_cheesing

import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import roland_a.mc_mods.common.ConditionalMixinLoader

class Plugin: IMixinConfigPlugin by base {
	companion object{
		private val base = ConditionalMixinLoader()
	}
}
