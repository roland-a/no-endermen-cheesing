package roland_a.mc_mods.no_endermen_cheesing.mixin;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.EnderMan.EndermanLookForPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import roland_a.mc_mods.no_endermen_cheesing.Mod;

import static net.minecraft.sounds.SoundEvents.ENDERMAN_TELEPORT;

@Mixin(EndermanLookForPlayerGoal.class)
final class EndermanLookForPlayerGoalMixin {
	private EndermanLookForPlayerGoalMixin(){}

	@Shadow @Final private EnderMan enderman;

	@Unique private int stuckFor = 0;

	@Inject(method = "tick", at = @At("HEAD"))
    private void tryTeleportUnreachablePlayers(CallbackInfo ci) {
		if (!(this.enderman.getTarget() instanceof Player player)) {
			return;
		}

		var config = Mod.getConfig();

		//Enderman is too far to be considered stuck
		//Timer should not be reset in case the enderman is only temporarily too far
		var distSq = this.enderman.position().distanceToSqr(player.position());
		var maxDistSq = Math.pow(config.distanceInBlocks(), 2);
		if (distSq > maxDistSq) {
			return;
		}

		//Enderman can freely attack player and should not be considered stuck
		if (this.enderman.isWithinMeleeAttackRange(player) && this.enderman.getSensing().hasLineOfSight(player)) {
            this.stuckFor = 0;
			return;
		}

		//Enderman is stuck, but cannot teleport player yet
		if (this.stuckFor < config.intervalInTicks()) {
            this.stuckFor += 1;
			return;
		}

		player.teleportTo(this.enderman.position().x, this.enderman.position().y, this.enderman.position().z);
		player.level().gameEvent(GameEvent.TELEPORT, this.enderman.position(), GameEvent.Context.of(player));
        this.enderman.playSound(ENDERMAN_TELEPORT, 1.0f, 1.0f);

        this.stuckFor = 0;
	}
}
