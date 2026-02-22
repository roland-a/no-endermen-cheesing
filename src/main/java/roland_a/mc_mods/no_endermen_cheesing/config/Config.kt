package roland_a.mc_mods.no_endermen_cheesing.config

import kotlinx.serialization.Serializable
import roland_a.mc_mods.common.units.Blocks
import roland_a.mc_mods.common.units.Blocks.Companion.blocks
import roland_a.mc_mods.common.units.Seconds
import roland_a.mc_mods.common.units.Seconds.Companion.seconds
import roland_a.mc_mods.common.units.Ticks.Companion.ticks

@Serializable
class Config(
	val distance: Blocks<Double> = 4.0.blocks,
	val interval: Seconds<Double> = 1.0.seconds
){
	init {
		require(distance > 0.0.blocks){
			distanceMustBePositive(distance)
		}

		require(interval >= 0.0.seconds){
			intervalMustBeNonNegative(interval)
		}
	}

	fun distanceInBlocks(): Double =
		distance.value

	fun intervalInTicks(): Double =
		interval.ticks.value

	companion object{
		internal fun distanceMustBePositive(distance: Blocks<Double>) =
			"DISTANCE MUST BE GREATER THAN ZERO, INSTEAD IT IS $distance"

		internal fun intervalMustBeNonNegative(maxDistance: Seconds<Double>) =
			"INTERVAL MUST BE GREATER THAN OR EQUAL TO ZERO, INSTEAD IT IS $maxDistance"
	}

}
