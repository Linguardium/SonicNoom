package mod.linguardium.sonicnoom.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import mod.linguardium.sonicnoom.ConfigManager;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SonicBoomTask.class)
public class SonicBoomMixin {
    @ModifyReturnValue(method={"shouldRun(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/mob/WardenEntity;)Z","shouldKeepRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/mob/WardenEntity;J)Z"}, at=@At("RETURN"))
    private boolean dontRunSonicBoom(boolean original, ServerWorld serverWorld) {
        if (serverWorld.getGameRules().getBoolean(ConfigManager.DISABLE_BOOM_RULE)) return false;
        return original;
    }
}
