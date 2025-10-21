package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import org.dyvinia.explosionrebalance.ExplosionRebalanceCommon;
import org.dyvinia.explosionrebalance.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "onExplosionHit", at = @At("HEAD"))
    private void addExplosionKnockback(Entity exploder, CallbackInfo ci) {
        if (!((Entity)(Object)this instanceof LivingEntity target))
            return;

        float radius = -1;

        if (exploder instanceof Creeper creeper && Config.CONFIG.enableCreeperKnockback.get())
            radius = (creeper.isPowered() ? 2f : 1f) * 4f;
        else if ((exploder instanceof PrimedTnt || exploder instanceof MinecartTNT) && Config.CONFIG.enableTNTKnockback.get())
            radius = 16f;

        if (radius > 0)
            ExplosionRebalanceCommon.applyKnockback(target, exploder, radius, Config.CONFIG);
    }
}
