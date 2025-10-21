package org.dyvinia.explosionrebalance;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.dyvinia.explosionrebalance.platform.Services;
import org.dyvinia.explosionrebalance.util.ExplosionOptions;

public class ExplosionRebalanceCommon {

    public static void init() {
        if (Services.PLATFORM.isModLoaded("explosionrebalance")) {
            Constants.LOG.info("ExplosionRebalance Loaded");
        }
    }

    public static void addKnockback(Entity target, Entity exploder, ExplosionOptions options) {
        float distance = target.distanceTo(exploder);
        double power = 1.0 - Math.pow(distance/(options.radius() * options.falloffExtension()), options.falloffExponent());

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
