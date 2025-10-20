package org.dyvinia.explosionrebalance.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Config CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.BooleanValue disableGriefing;

    public final ModConfigSpec.ConfigValue<Double> knockbackMult;
    public final ModConfigSpec.ConfigValue<Double> playerKnockbackMult;
    public final ModConfigSpec.ConfigValue<Double> knockbackUp;
    public final ModConfigSpec.ConfigValue<Double> falloffExponent;


    private Config(ModConfigSpec.Builder builder) {
        disableGriefing = builder
                .comment("\n Prevents creeper explosions from breaking blocks. \n Default: true")
                .define("disableGriefing", true);

        knockbackMult = builder
                .comment("\n Multiplier for extra knockback caused by the explosion.\n Set to 0.0 for vanilla behavior.\n Default: 4.0")
                .define("knockbackMult", 4.0);

        playerKnockbackMult = builder
                .comment("\n Multiplier for extra knockback caused by the explosion to players.\n Set to -1.0 to default to the value of knockbackMult.\n Default: 3.5")
                .define("playerKnockbackMult", 3.5);

        knockbackUp = builder
                .comment("\n Adjusts the upward velocity added by the explosion as part of its knockback.\n Default: 0.1")
                .define("knockbackUp", 0.1);

        falloffExponent = builder
                .comment("\n Exponent used for calculating the falloff.\n Default: 2.5")
                .define("falloffExponent", 2.5);
    }

    static {
        Pair<Config, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(Config::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
