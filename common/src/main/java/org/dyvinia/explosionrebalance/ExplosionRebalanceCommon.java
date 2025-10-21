package org.dyvinia.explosionrebalance;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.dyvinia.explosionrebalance.config.Config;
import org.dyvinia.explosionrebalance.platform.Services;

public class ExplosionRebalanceCommon {

    public static void init() {
        if (Services.PLATFORM.isModLoaded("explosionrebalance")) {
            Constants.LOG.info("ExplosionRebalance Loaded");
        }
    }

    public static void applyKnockback(Entity target, Entity exploder, float radius, Config config) {
        float distance = target.distanceTo(exploder);
        double power = 1.0 - Math.pow(distance/radius, config.falloffExponent.get());

        if (power > 0) {
            double knockback = power;
            if (config.playerKnockbackMult.get() >= 0 && target instanceof Player)
                knockback *= config.playerKnockbackMult.get();
            else
                knockback *= config.knockbackMult.get();

            Vec3 direction = target.position().subtract(exploder.position()).normalize();
            Vec3 velocity = new Vec3(direction.x, config.knockbackUp.get(), direction.z);
            velocity = velocity.scale(knockback);
            target.addDeltaMovement(velocity);
        }
    }
}
