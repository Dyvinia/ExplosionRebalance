package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import org.dyvinia.explosionrebalance.ExplosionRebalanceCommon;
import org.dyvinia.explosionrebalance.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "onExplosionHit", at = @At("HEAD"))
    private void addExplosionKnockback(Entity entity, CallbackInfo ci) {
        Entity thisEntity = (Entity)(Object)this;

        if (entity instanceof Creeper creeper && Config.CONFIG.enableCreeperKnockback.get()) {
            ExplosionRebalanceCommon.applyKnockback(
                    thisEntity,
                    creeper,
                    (creeper.isPowered() ? 2.0f : 1.0f) * 4.0f,
                    Config.CONFIG
            );
        }
        else if (!(thisEntity instanceof PrimedTnt) && entity instanceof PrimedTnt primedTnt && Config.CONFIG.enableTNTKnockback.get()) {
            ExplosionRebalanceCommon.applyKnockback(
                    thisEntity,
                    primedTnt,
                    16.0f,
                    Config.CONFIG
            );
        }
    }
}
