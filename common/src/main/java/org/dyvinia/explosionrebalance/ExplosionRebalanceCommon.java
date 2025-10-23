package org.dyvinia.explosionrebalance;

import org.dyvinia.explosionrebalance.platform.Services;

public class ExplosionRebalanceCommon {
    public static void init() {
        if (Services.PLATFORM.isModLoaded("explosionrebalance")) {
            Constants.LOG.info("Explosion Control & Rebalance Loaded");
        }
    }
}
