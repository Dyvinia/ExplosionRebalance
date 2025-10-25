package org.dyvinia.explosionrebalance.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
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
        double damageMult,
        float radius
) {
    public double knockbackStrength() {
        return Config.CONFIG.knockbackStrength.get() * knockbackMult;
    }
    public double playerKnockbackStrength() {
        return knockbackStrength() * Config.CONFIG.playerKnockbackMult.get();
    }
    public double upwardsKnockback() {
        return Config.CONFIG.upwardsKnockback.get();
    }

    public double falloffExponent() {
        return Config.CONFIG.falloffExponent.get();
    }
    public double falloffExtension() {
        return Config.CONFIG.falloffExtension.get();
    }

    public float damageMultiplier() {
        return (float) (damageMult > 0 ? damageMult : 0.01);
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
                    Config.CONFIG.creeperDamageMult.get(),
                    Optional.ofNullable(radius).orElse((creeper.isPowered() ? 2f : 1f) * 3f)
            );
        }
        else if (ent instanceof EndCrystal) {
            return new ExplosionOptions(
                    !Config.CONFIG.disableEndCrystalGriefing.get(),
                    Config.CONFIG.enableEndCrystalKnockback.get(),
                    Config.CONFIG.endCrystalKnockbackMult.get(),
                    Config.CONFIG.endCrystalDamageMult.get(),
                    Optional.ofNullable(radius).orElse(6f)
            );
        }
        else if (ent instanceof Fireball) {
            return new ExplosionOptions(
                    !Config.CONFIG.disableFireballGriefing.get(),
                    Config.CONFIG.enableFireballKnockback.get(),
                    Config.CONFIG.fireballKnockbackMult.get(),
                    Config.CONFIG.fireballDamageMult.get(),
                    Optional.ofNullable(radius).orElse(1f)
            );
        }
        else if (ent instanceof PrimedTnt || ent instanceof MinecartTNT) {
            return new ExplosionOptions(
                    !Config.CONFIG.disableTNTGriefing.get(),
                    Config.CONFIG.enableTNTKnockback.get(),
                    Config.CONFIG.tntKnockbackMult.get(),
                    Config.CONFIG.tntDamageMult.get(),
                    Optional.ofNullable(radius).orElse(4f)
            );
        }
        else if (ent != null
                && !ent.getType().getDescriptionId().isEmpty()
                && !Config.CONFIG.customExplosions.get().isEmpty()
                && !Config.CONFIG.customExplosions.get().getFirst().isEmpty()) {
            var customExploder = Config.CONFIG.customExplosions.get().stream()
                    .filter(o -> ent.getType().getDescriptionId().equalsIgnoreCase((String) o.getFirst()))
                    .findFirst();

            if (customExploder.isPresent()) {
                return new ExplosionOptions(
                        !(boolean) customExploder.get().get(1),
                        (boolean) customExploder.get().get(2),
                        (double) customExploder.get().get(3),
                        (double) customExploder.get().get(4),
                        Optional.ofNullable(radius).orElse(3f)
                );
            }
        }

        return null;
    }
}