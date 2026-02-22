package roland_a.mc_mods.no_endermen_cheesing.config

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import roland_a.mc_mods.common.assertEquals
import roland_a.mc_mods.common.assertThrowsMessage
import roland_a.mc_mods.common.units.Blocks.Companion.blocks
import roland_a.mc_mods.common.units.Seconds.Companion.seconds
import roland_a.mc_mods.common.units.Ticks.Companion.ticks

private class ConfigTest {
	@Test
	fun `getters are properly mapped to respective fields`(){
		val config = Config(
			1.23.blocks,
			4.56.seconds
		)

		config
		.intervalInTicks()
		.assertEquals(
			config.interval.ticks.value
		)

		config
		.distanceInBlocks()
		.assertEquals(
			config.distance.value
		)
	}

	@Nested
	inner class DistanceTest{
		@Test
		fun `positive max distance is allowed`() {
			assertDoesNotThrow {
				Config(distance = 1.0.blocks)
			}
		}

		@Test
		fun `zero max distance is not allowed`() {
			val distance = 0.0.blocks

			assertThrowsMessage<IllegalArgumentException>(
				Config.distanceMustBePositive(distance)
			) {
				Config(distance = distance)
			}
		}

		@Test
		fun `negative max distances is not allowed`() {
			val distance = (-1.0).blocks

			assertThrowsMessage<IllegalArgumentException>(
				Config.distanceMustBePositive(distance)
			) {
				Config(distance = distance)
			}
		}
	}

	@Nested
	inner class IntervalTest{
		@Test
		fun `positive interval is allowed`() {
			val interval = 1.0.seconds

			assertDoesNotThrow {
				Config(interval = interval)
			}
		}

		@Test
		fun `zero interval is allowed`() {
			val interval = 0.0.seconds

			assertDoesNotThrow {
				Config(interval = interval)
			}
		}

		@Test
		fun `negative interval is not allowed`() {
			val maxDistance = (-1.0).seconds

			assertThrowsMessage<IllegalArgumentException>(
				Config.intervalMustBeNonNegative(maxDistance)
			) {
				Config(interval = maxDistance)
			}
		}
	}
}
