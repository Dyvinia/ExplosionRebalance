package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import org.dyvinia.explosionrebalance.ExplosionRebalanceCommon;
import org.dyvinia.explosionrebalance.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "onExplosionHit", at = @At("HEAD"), cancellable = true)
    private void explode(Entity entity, CallbackInfo ci) {
        if (entity instanceof Creeper creeper) {
            ExplosionRebalanceCommon.applyKnockback(
                    (Entity) (Object) this,
                    creeper,
                    (creeper.isPowered() ? 2.0f : 1.0f) * 4.0f,
                    Config.CONFIG.falloffExponent.get(),
                    Config.CONFIG.knockbackMult.get(),
                    Config.CONFIG.playerKnockbackMult.get(),
                    Config.CONFIG.knockbackUp.get()
            );
        }
    }
}
