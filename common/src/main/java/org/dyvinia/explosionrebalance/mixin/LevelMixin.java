package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
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
    @Inject(method = "explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)V", at = @At("HEAD"), cancellable = true)
    private void overrideExplosion(@Nullable Entity pSource, DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pX, double pY, double pZ, float pRadius, boolean pFire, Level.ExplosionInteraction pExplosionInteraction, ParticleOptions pSmallExplosionParticles, ParticleOptions pLargeExplosionParticles, Holder<SoundEvent> pExplosionSound, CallbackInfo ci) {
        ServerLevel serverlevel = (ServerLevel) (Object) this;
        ExplosionOptions options = ExplosionOptions.from(pSource, pRadius);
        if (pSource == null || options == null)
            return;

        ((IEntityExplosionOptions) pSource).explosionRebalance$setExplosionOptions(options);

        // override explosion without griefing
        if (!options.griefing()) {
            ParticleOptions particles = pRadius >= 2f ? pLargeExplosionParticles : pSmallExplosionParticles;
            Vec3 vec3 = new Vec3(pX, pY, pZ);

            ServerExplosion serverexplosion = new ServerExplosion(
                    serverlevel,
                    pSource, pDamageSource,
                    pDamageCalculator,
                    vec3,
                    pRadius,
                    false,
                    Explosion.BlockInteraction.KEEP
            );
            serverexplosion.explode();

            for (ServerPlayer serverplayer : serverlevel.players()) {
                if (serverplayer.distanceToSqr(vec3) < 4096.0) {
                    Optional<Vec3> optional = Optional.ofNullable(serverexplosion.getHitPlayers().get(serverplayer));
                    serverplayer.connection.send(new ClientboundExplodePacket(vec3, optional, particles, pExplosionSound));
                }
            }

            ci.cancel();
        }
    }
}
