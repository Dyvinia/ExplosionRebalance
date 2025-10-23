package org.dyvinia.explosionrebalance.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import org.dyvinia.explosionrebalance.config.Config;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record ExplosionOptions(
        boolean griefing,
        boolean knockback,
        double knockbackMult,
        float radius
) {
    public double knockbackStrength() {
        return Config.CONFIG.knockbackStrength.get() * knockbackMult;
    }
    public double playerKnockbackStrength() {
        return knockbackStrength() * Config.CONFIG.playerKnockbackMult.get();
    }
    public double knockbackUp() {
        return Config.CONFIG.knockbackUp.get();
    }

    public double falloffExponent() {
        return Config.CONFIG.falloffExponent.get();
    }
    public double falloffExtension() {
        return Config.CONFIG.falloffExtension.get();
    }

    public double damageMultiplier() {
        return 0.25;
    }

    @Nullable
    public static ExplosionOptions from(@Nullable Entity ent) {
        return from(ent, null);
    }

    @Nullable
    public static ExplosionOptions from(@Nullable Entity ent, @Nullable Float radius) {
        if (ent instanceof Creeper creeper) {
            return new ExplosionOptions(
                    !Config.CONFIG.disableCreeperGriefing.get(),
                    Config.CONFIG.enableCreeperKnockback.get(),
                    Config.CONFIG.creeperKnockbackMult.get(),
                    Optional.ofNullable(radius).orElse((creeper.isPowered() ? 2f : 1f) * 3f)
            );
        }
        else if (ent instanceof Fireball) {
            return new ExplosionOptions(
                    !Config.CONFIG.disableFireballGriefing.get(),
                    Config.CONFIG.enableFireballKnockback.get(),
                    Config.CONFIG.fireballKnockbackMult.get(),
                    Optional.ofNullable(radius).orElse(1f)
            );
        }
        else if (ent instanceof PrimedTnt || ent instanceof MinecartTNT) {
            return new ExplosionOptions(
                    !Config.CONFIG.disableTNTGriefing.get(),
                    Config.CONFIG.enableTNTKnockback.get(),
                    Config.CONFIG.tntKnockbackMult.get(),
                    Optional.ofNullable(radius).orElse(4f)
            );
        }

        return null;
    }
}