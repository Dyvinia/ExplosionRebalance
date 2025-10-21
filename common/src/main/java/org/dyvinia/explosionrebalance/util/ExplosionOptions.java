package org.dyvinia.explosionrebalance.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import org.dyvinia.explosionrebalance.config.Config;
import org.jetbrains.annotations.Nullable;

public record ExplosionOptions(
        boolean griefing,
        boolean knockback,
        float radius
) {
    @Nullable
    public static ExplosionOptions from(@Nullable Entity ent) {
        if (ent instanceof Creeper creeper) {
            return new ExplosionOptions(
                    !Config.CONFIG.disableCreeperGriefing.get(),
                    Config.CONFIG.enableCreeperKnockback.get(),
                    (creeper.isPowered() ? 2f : 1f) * 4f
            );
        }
        else if (ent instanceof PrimedTnt || ent instanceof MinecartTNT) {
            return new ExplosionOptions(
                    !Config.CONFIG.disableTNTGriefing.get(),
                    Config.CONFIG.enableTNTKnockback.get(),
                    16f
            );
        }

        return null;
    }
}