package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.world.phys.Vec3;
import org.dyvinia.explosionrebalance.util.ExplosionOptions;
import org.dyvinia.explosionrebalance.util.IEntityExplosionOptions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerLevel.class)
public abstract class LevelMixin {
    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    private void overrideExplosion(@Nullable Entity source, DamageSource damageSource, @Nullable ExplosionDamageCalculator damageCalculator, double x, double y, double z, float r, boolean fire, Level.ExplosionInteraction interactionType, ParticleOptions smallExplosionParticles, ParticleOptions largeExplosionParticles, WeightedList<ExplosionParticleInfo> blockParticles, Holder<SoundEvent> explosionSound, CallbackInfo ci) {
        ServerLevel serverlevel = (ServerLevel) (Object) this;
        ExplosionOptions options = ExplosionOptions.from(source, r);
        if (source == null || options == null)
            return;

        ((IEntityExplosionOptions) source).explosionRebalance$setExplosionOptions(options);

        // override explosion without griefing
        if (!options.griefing()) {
            ParticleOptions particles = r >= 2f ? largeExplosionParticles : smallExplosionParticles;
            Vec3 center = new Vec3(x, y, z);

            ServerExplosion serverexplosion = new ServerExplosion(
                    serverlevel,
                    source, damageSource,
                    damageCalculator,
                    center,
                    r,
                    false,
                    Explosion.BlockInteraction.KEEP
            );
            int blockCount = serverexplosion.explode();

            for (ServerPlayer serverplayer : serverlevel.players()) {
                if (serverplayer.distanceToSqr(center) < 4096.0) {
                    Optional<Vec3> playerKnockback = Optional.ofNullable(serverexplosion.getHitPlayers().get(serverplayer));
                    serverplayer.connection.send(new ClientboundExplodePacket(center, r, blockCount, playerKnockback, particles, explosionSound, blockParticles));
                }
            }

            ci.cancel();
        }
    }
}
