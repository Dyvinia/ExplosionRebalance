package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import org.dyvinia.explosionrebalance.util.ExplosionOptions;
import org.dyvinia.explosionrebalance.util.IEntityExplosionOptions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExplosionDamageCalculator.class)
public abstract class ExplosionDamageCalculatorMixin {
    @Inject(method = "getEntityDamageAmount", at = @At("TAIL"), cancellable = true)
    private void addExplosionDamage(Explosion explosion, Entity entity, float seenPercent, CallbackInfoReturnable<Float> cir) {
        if (!(explosion.getDirectSourceEntity() instanceof Entity exploder))
            return;

        @Nullable ExplosionOptions options = ((IEntityExplosionOptions)exploder).explosionRebalance$getExplosionOptions();
        if (options == null)
            options = ExplosionOptions.from(exploder);
        if (options == null)
            return;

        cir.setReturnValue(cir.getReturnValue() * options.damageMultiplier());
    }
}
