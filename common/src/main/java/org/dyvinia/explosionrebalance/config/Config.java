package org.dyvinia.explosionrebalance.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Config CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.BooleanValue disableCreeperGriefing;
    public final ModConfigSpec.BooleanValue enableCreeperKnockback;
    public final ModConfigSpec.ConfigValue<Double> creeperKnockbackMult;

    public final ModConfigSpec.BooleanValue disableTNTGriefing;
    public final ModConfigSpec.BooleanValue enableTNTKnockback;
    public final ModConfigSpec.ConfigValue<Double> tntKnockbackMult;

    public final ModConfigSpec.ConfigValue<Double> knockbackStrength;
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
        creeperKnockbackMult = builder
                .comment(" Multiplier for extra knockback caused by creeper explosions.")
                .comment(" Default: 1.0")
                .define("CreeperKnockbackMultiplier", 1.0);
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
        tntKnockbackMult = builder
                .comment(" Multiplier for extra knockback caused by TNT explosions.")
                .comment(" Default: 1.0")
                .define("TNTKnockbackMultiplier", 1.0);
        builder.pop();

        builder.pop();

        builder.comment(" Controls aspects of the added knockback").push("Knockback");
        knockbackStrength = builder
                .comment(" Strength of extra knockback caused by the explosion.")
                .comment(" Default: 4.0")
                .define("KnockbackStrength", 4.0);
        playerKnockbackMult = builder
                .comment(" Multiplier of extra knockback caused by the explosion to players.")
                .comment(" Default: 0.85")
                .define("PlayerKnockbackMultiplier", 0.85);
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
}
