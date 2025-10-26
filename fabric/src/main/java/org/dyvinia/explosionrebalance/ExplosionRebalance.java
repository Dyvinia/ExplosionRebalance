package org.dyvinia.explosionrebalance;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;
import org.dyvinia.explosionrebalance.config.Config;

public class ExplosionRebalance implements ModInitializer {
    @Override
    public void onInitialize() {
        ExplosionRebalanceCommon.init();

        NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, Config.CONFIG_SPEC);
    }
}
