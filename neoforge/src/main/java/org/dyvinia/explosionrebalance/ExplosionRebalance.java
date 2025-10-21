package org.dyvinia.explosionrebalance;


import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import org.dyvinia.explosionrebalance.config.Config;

@Mod(Constants.MOD_ID)
public class ExplosionRebalance {

    public ExplosionRebalance(IEventBus eventBus, ModContainer container) {
        Constants.LOG.info("Hello NeoForge world!");
        ExplosionRebalanceCommon.init();

        container.registerConfig(ModConfig.Type.SERVER, Config.CONFIG_SPEC);

        NeoForge.EVENT_BUS.addListener(ExplosionRebalance::onExplosionStart);
        NeoForge.EVENT_BUS.addListener(ExplosionRebalance::onExplosionDetonate);
    }

    public static void onExplosionStart(ExplosionEvent.Start event) {
        if (Config.CONFIG.disableCreeperGriefing.get() && event.getExplosion().getDirectSourceEntity() instanceof Creeper creeper) {
            event.setCanceled(true);

            Explosion explosion = new Explosion(creeper.level(), creeper, creeper.getX(), creeper.getY(), creeper.getZ(), (creeper.isPowered() ? 2.0f : 1.0f) * 3.0f, false,  Explosion.BlockInteraction.KEEP);
            explosion.explode();
        }
    }

    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        event.getAffectedEntities().forEach(entity -> {
            if (event.getExplosion().getDirectSourceEntity() instanceof Creeper creeper && Config.CONFIG.enableCreeperKnockback.get()) {
                ExplosionRebalanceCommon.applyKnockback(
                        entity,
                        creeper,
                        (creeper.isPowered() ? 2.0f : 1.0f) * 4.0f,
                        Config.CONFIG
                );
            }
        });
    }
}
