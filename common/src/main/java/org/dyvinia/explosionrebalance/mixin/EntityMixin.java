package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.dyvinia.explosionrebalance.util.ExplosionOptions;
import org.dyvinia.explosionrebalance.util.IEntityExplosionOptions;
import org.jetbrains.annotations.Nullable;
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

        // i hate java this would be half as many lines and easier to read in c#....fml
        @Nullable ExplosionOptions options = ((IEntityExplosionOptions) exploder).explosionRebalance$getExplosionOptions();
        if (options == null)
            options = ExplosionOptions.from(exploder);
        if (options == null)
            return;

        if (options.knockback()) {
            float distance = target.distanceTo(exploder);
            double power = 1.0 - Math.pow(distance/((options.radius() * 2.0) + options.falloffExtension()), options.falloffExponent());

            if (power > 0) {
                double knockback = power;
                if (options.playerKnockbackStrength() >= 0 && target instanceof Player)
                    knockback *= options.playerKnockbackStrength();
                else
                    knockback *= options.knockbackStrength();

                Vec3 direction = target.position().subtract(exploder.position()).normalize();
                Vec3 velocity = new Vec3(direction.x, options.knockbackUp(), direction.z);
                velocity = velocity.scale(knockback);
                target.addDeltaMovement(velocity);
            }
        }
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
