package org.dyvinia.explosionrebalance;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.dyvinia.explosionrebalance.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

public class ExplosionRebalanceCommon {

    public static void init() {
        if (Services.PLATFORM.isModLoaded("explosionrebalance")) {
            Constants.LOG.info("Hello to explosionrebalance");
        }
    }

    public static void applyKnockback(Entity target, Entity exploder, float radius, double falloffExponent, double knockbackMult, double playerKnockbackMult, double knockbackUp) {
        float distance = target.distanceTo(exploder);
        double power = 1.0f - Math.pow(distance/radius, falloffExponent);

        if (power > 0) {
            double knockback = power;
            if (playerKnockbackMult >= 0 && target instanceof Player)
                knockback *= playerKnockbackMult;
            else
                knockback *= knockbackMult;

            Vec3 direction = target.position().subtract(exploder.position()).normalize();
            Vec3 velocity = new Vec3(direction.x, knockbackUp, direction.z);
            velocity = velocity.scale(knockback);
            target.addDeltaMovement(velocity);
        }
    }
}
