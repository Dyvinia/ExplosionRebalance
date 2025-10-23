package org.dyvinia.explosionrebalance.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Config CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<Double> knockbackStrength;
    public final ModConfigSpec.ConfigValue<Double> playerKnockbackMult;
    public final ModConfigSpec.ConfigValue<Double> knockbackUp;

    public final ModConfigSpec.ConfigValue<Double> falloffExponent;
    public final ModConfigSpec.ConfigValue<Double> falloffExtension;

    public final ModConfigSpec.BooleanValue disableCreeperGriefing;
    public final ModConfigSpec.BooleanValue enableCreeperKnockback;
    public final ModConfigSpec.ConfigValue<Double> creeperKnockbackMult;
    public final ModConfigSpec.ConfigValue<Double> creeperDamageMult;

    public final ModConfigSpec.BooleanValue disableEndCrystalGriefing;
    public final ModConfigSpec.BooleanValue enableEndCrystalKnockback;
    public final ModConfigSpec.ConfigValue<Double> endCrystalKnockbackMult;
    public final ModConfigSpec.ConfigValue<Double> endCrystalDamageMult;

    public final ModConfigSpec.BooleanValue disableFireballGriefing;
    public final ModConfigSpec.BooleanValue enableFireballKnockback;
    public final ModConfigSpec.ConfigValue<Double> fireballKnockbackMult;
    public final ModConfigSpec.ConfigValue<Double> fireballDamageMult;

    public final ModConfigSpec.BooleanValue disableTNTGriefing;
    public final ModConfigSpec.BooleanValue enableTNTKnockback;
    public final ModConfigSpec.ConfigValue<Double> tntKnockbackMult;
    public final ModConfigSpec.ConfigValue<Double> tntDamageMult;

    private Config(ModConfigSpec.Builder builder) {
        builder.comment(" Controls aspects of the added knockback").push("Knockback");
        knockbackStrength = builder
                .comment(" Strength of extra knockback caused by the explosion.")
                .comment(" Default: 3.0")
                .define("KnockbackStrength", 3.0);
        playerKnockbackMult = builder
                .comment(" Multiplier of extra knockback caused by the explosion to players.")
                .comment(" Default: 0.9")
                .define("PlayerKnockbackMultiplier", 0.9);
        knockbackUp = builder
                .comment(" Adjusts the upward velocity added by the explosion as part of its knockback.")
                .comment(" Default: 0.1")
                .define("KnockbackUp", 0.1);
        builder.pop();

        builder.comment(" Controls the falloff used for knockback and damage").push("Falloff");
        falloffExponent = builder
                .comment(" Exponent used for calculating the falloff.")
                .comment(" Default: 2.0")
                .define("FalloffExponent", 2.0);
        falloffExtension = builder
                .comment(" Adds to the radius when calculating the falloff. Allows for entities near the edge of the explosion to still get some knockback")
                .comment(" Default: 1.0")
                .define("FalloffExtension", 1.0);
        builder.pop();

        builder.comment(" Controls aspects of different types of explosions").push("Explosions");

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
        creeperDamageMult = builder
                .comment(" Multiplier for the total damage caused by creeper explosions.")
                .comment(" Default: 1.0")
                .define("CreeperDamageMultiplier", 1.0);
        builder.pop();

        builder.push("EndCrystal");
        disableEndCrystalGriefing = builder
                .comment(" Prevents end crystal explosions from breaking blocks and setting blocks on fire.")
                .comment(" Default: false")
                .define("DisableEndCrystalGriefing", false);
        enableEndCrystalKnockback = builder
                .comment(" Enables extra knockback for end crystal explosions.")
                .comment(" Default: true")
                .define("EnableEndCrystalKnockback", true);
        endCrystalKnockbackMult = builder
                .comment(" Multiplier for extra knockback caused by end crystal explosions.")
                .comment(" Default: 1.0")
                .define("EndCrystalKnockbackMultiplier", 1.0);
        endCrystalDamageMult = builder
                .comment(" Multiplier for the total damage caused by end crystal explosions.")
                .comment(" Default: 0.75")
                .define("EndCrystalDamageMultiplier", 0.75);
        builder.pop();

        builder.push("Fireball");
        disableFireballGriefing = builder
                .comment(" Prevents fireball explosions from breaking blocks and setting blocks on fire.")
                .comment(" Default: false")
                .define("DisableFireballGriefing", false);
        enableFireballKnockback = builder
                .comment(" Enables extra knockback for fireball explosions.")
                .comment(" Default: true")
                .define("EnableFireballKnockback", true);
        fireballKnockbackMult = builder
                .comment(" Multiplier for extra knockback caused by fireball explosions.")
                .comment(" Default: 0.75")
                .define("FireballKnockbackMultiplier", 0.75);
        fireballDamageMult = builder
                .comment(" Multiplier for the total damage caused by fireball explosions.")
                .comment(" Default: 1.0")
                .define("FireballDamageMultiplier", 1.0);
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
                .comment(" Default: 1.25")
                .define("TNTKnockbackMultiplier", 1.25);
        tntDamageMult = builder
                .comment(" Multiplier for the total damage caused by TNT explosions.")
                .comment(" Default: 1.0")
                .define("TNTDamageMultiplier", 1.0);
        builder.pop();

        builder.pop();
    }

    static {
        Pair<Config, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Config::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
