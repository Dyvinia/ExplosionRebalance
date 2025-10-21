package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.dyvinia.explosionrebalance.ExplosionRebalanceCommon;
import org.dyvinia.explosionrebalance.util.ExplosionOptions;
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

        ExplosionOptions options = ExplosionOptions.from(exploder);
        if (options != null && options.knockback())
            ExplosionRebalanceCommon.addKnockback(target, exploder, options);
    }
}
