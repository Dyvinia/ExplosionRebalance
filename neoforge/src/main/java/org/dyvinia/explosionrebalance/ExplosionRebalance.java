package org.dyvinia.explosionrebalance;


import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.dyvinia.explosionrebalance.config.Config;

@Mod(Constants.MOD_ID)
public class ExplosionRebalance {
    public ExplosionRebalance(ModContainer container) {
        ExplosionRebalanceCommon.init();

        container.registerConfig(ModConfig.Type.SERVER, Config.CONFIG_SPEC);
    }
}
