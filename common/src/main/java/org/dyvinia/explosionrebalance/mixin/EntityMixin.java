package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.dyvinia.explosionrebalance.ExplosionRebalanceCommon;
import org.dyvinia.explosionrebalance.util.ExplosionOptions;
import org.dyvinia.explosionrebalance.util.IEntityExplosionOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityExplosionOptions {
    @Unique
    private ExplosionOptions explosionRebalance$explosionOptions;

    @Inject(method = "onExplosionHit", at = @At("HEAD"))
    private void addExplosionKnockback(Entity exploder, CallbackInfo ci) {
        if (!((Entity)(Object)this instanceof LivingEntity target))
            return;

        ExplosionOptions options = ((IEntityExplosionOptions) exploder).explosionRebalance$getExplosionOptions();
        if (options == null)
            options = ExplosionOptions.from(exploder);

        if (options != null && options.knockback())
            ExplosionRebalanceCommon.addKnockback(target, exploder, options);
    }

    @Override
    public void explosionRebalance$setExplosionOptions(ExplosionOptions options) {
        explosionRebalance$explosionOptions = options;
    }

    @Override
    public ExplosionOptions explosionRebalance$getExplosionOptions() {
        return explosionRebalance$explosionOptions;
    }


}
