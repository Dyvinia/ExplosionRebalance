package org.dyvinia.explosionrebalance.config;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

public class Config {
    public static final Config CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.BooleanValue disableCreeperGriefing;
    public final ModConfigSpec.BooleanValue enableCreeperKnockback;

    public final ModConfigSpec.BooleanValue disableTNTGriefing;
    public final ModConfigSpec.BooleanValue enableTNTKnockback;

    public final ModConfigSpec.ConfigValue<Double> knockbackMult;
    public final ModConfigSpec.ConfigValue<Double> playerKnockbackMult;
    public final ModConfigSpec.ConfigValue<Double> knockbackUp;
    public final ModConfigSpec.ConfigValue<Double> falloffExponent;


    private Config(ModConfigSpec.Builder builder) {
        builder.comment(" Options for different explosions").push("Explosions");

        builder.push("Creeper");
        disableCreeperGriefing = builder
                .comment(" Prevents creeper explosions from breaking blocks.")
                .comment(" Default: true")
                .define("DisableCreeperGriefing", true);
        enableCreeperKnockback = builder
                .comment(" Enables extra knockback for creeper explosions.")
                .comment(" Default: true")
                .define("EnableCreeperKnockback", true);
        builder.pop();

        builder.push("TNT");
        disableTNTGriefing = builder
                .comment(" Prevents TNT explosions from breaking blocks.")
                .comment(" Default: false")
                .define("DisableTNTGriefing", false);
        enableTNTKnockback = builder
                .comment(" Enables extra knockback for TNT explosions.")
                .comment(" Default: true")
                .define("EnableTNTKnockback", true);
        builder.pop();

        builder.pop();

        builder.comment(" Controls aspects of the added knockback").push("Knockback");
        knockbackMult = builder
                .comment(" Multiplier for extra knockback caused by the explosion.\n Set to 0.0 for vanilla behavior.")
                .comment(" Default: 4.0")
                .define("KnockbackMult", 4.0);
        playerKnockbackMult = builder
                .comment(" Multiplier for extra knockback caused by the explosion to players.")
                .comment(" Set to -1.0 to default to the value of knockbackMult.")
                .comment(" Default: 3.5")
                .define("PlayerKnockbackMult", 3.5);
        knockbackUp = builder
                .comment(" Adjusts the upward velocity added by the explosion as part of its knockback.")
                .comment(" Default: 0.1")
                .define("KnockbackUp", 0.1);
        falloffExponent = builder
                .comment(" Exponent used for calculating the falloff.")
                .comment(" Default: 2.5")
                .define("FalloffExponent", 2.5);
        builder.pop();
    }

    static {
        Pair<Config, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Config::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }

    public record Options(
            boolean griefing,
            boolean knockback,
            float radius
    ) {}

    @Nullable
    public Options getOptions(@Nullable Entity ent) {
        if (ent instanceof Creeper creeper)
            return new Options(
                    !disableCreeperGriefing.get(),
                    enableCreeperKnockback.get(),
                    (creeper.isPowered() ? 2f : 1f) * 4f
            );
        else if (ent instanceof PrimedTnt || ent instanceof MinecartTNT)
            return new Options(
                    !disableTNTGriefing.get(),
                    enableTNTKnockback.get(),
                    16f
            );

        return null;
    }
}
