package org.dyvinia.explosionrebalance;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
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
        Entity entity = event.getExplosion().getDirectSourceEntity();

        if (Config.CONFIG.disableCreeperGriefing.get() && entity instanceof Creeper)
            event.setCanceled(true);
        else if (Config.CONFIG.disableTNTGriefing.get() && entity instanceof PrimedTnt)
            event.setCanceled(true);

        if (event.isCanceled()) {
            Explosion explosion = new Explosion(entity.level(), entity, entity.getX(), entity.getY(), entity.getZ(), event.getExplosion().radius(), false,  Explosion.BlockInteraction.KEEP);
            explosion.explode();
        }
    }

    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        event.getAffectedEntities().forEach(entity -> {
            Entity exploder = event.getExplosion().getDirectSourceEntity();

            if (exploder instanceof Creeper creeper && Config.CONFIG.enableCreeperKnockback.get()) {
                ExplosionRebalanceCommon.applyKnockback(
                        entity,
                        creeper,
                        (creeper.isPowered() ? 2.0f : 1.0f) * 4.0f,
                        Config.CONFIG
                );
            }
            else if (!(entity instanceof PrimedTnt) && exploder instanceof PrimedTnt primedTnt && Config.CONFIG.enableTNTKnockback.get()) {
                ExplosionRebalanceCommon.applyKnockback(
                        entity,
                        primedTnt,
                        16.0f,
                        Config.CONFIG
                );
            }
        });
    }
}
