package mod.linguardium.sonicnoom.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.linguardium.sonicnoom.ConfigManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.entity.damage.DamageTypes.SONIC_BOOM;

@Mixin(LivingEntity.class)
public abstract class LivingEntitySonicBoomShielding extends Entity {

    @Shadow public abstract void damageShield(float amount);

    public LivingEntitySonicBoomShielding(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyExpressionValue(method="blockedByShield", at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean SonicBoomShieldHandler(boolean original, DamageSource source) {
        if (!original) return original; // was not in bypasses shield
        if (!source.isOf(SONIC_BOOM)) return original;
        return !getWorld().getGameRules().getBoolean(ConfigManager.SHIELDS_BLOCK_BOOM);
    }

    @WrapOperation(method="damage", at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V"))
    private void SonicBoomHurtsShields(LivingEntity instance, float amount, Operation<Void> damageShieldOperation, DamageSource source) {
        if (getWorld().getGameRules().getBoolean(ConfigManager.SHIELDS_BLOCK_BOOM) && source.isOf(SONIC_BOOM)) amount *= 10;
        damageShieldOperation.call(instance, amount);
    }

    @Inject(method="damage",at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeShieldHit(Lnet/minecraft/entity/LivingEntity;)V", shift = At.Shift.AFTER))
    private void sonicBoomDisablesShield(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOf(SONIC_BOOM) && getWorld().getGameRules().getBoolean(ConfigManager.SHIELDS_BLOCK_BOOM)) {
            if ((Object)this instanceof PlayerEntity player) {
                player.disableShield();
            }
        }
    }
}
