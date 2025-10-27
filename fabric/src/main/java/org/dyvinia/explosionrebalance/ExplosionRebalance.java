package org.dyvinia.explosionrebalance;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;
import org.dyvinia.explosionrebalance.config.Config;

public class ExplosionRebalance implements ModInitializer {
    @Override
    public void onInitialize() {
        ExplosionRebalanceCommon.init();

        ConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, Config.CONFIG_SPEC);
    }
}
