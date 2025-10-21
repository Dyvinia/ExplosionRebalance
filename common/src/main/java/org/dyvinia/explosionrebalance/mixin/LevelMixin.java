package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;

import org.dyvinia.explosionrebalance.config.Config;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class LevelMixin {
    @Inject(method = "explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;ZLnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)Lnet/minecraft/world/level/Explosion;", at = @At("HEAD"), cancellable = true)
    private void overrideExplosion(@Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pX, double pY, double pZ, float pRadius, boolean pFire, Level.ExplosionInteraction pExplosionInteraction, boolean pSpawnParticles, ParticleOptions pSmallExplosionParticles, ParticleOptions pLargeExplosionParticles, Holder<SoundEvent> pExplosionSound, CallbackInfoReturnable<Explosion> cir) {
        boolean override = false;

        if (pSource instanceof Creeper && Config.CONFIG.disableCreeperGriefing.get())
            override = true;
        else if ((pSource instanceof PrimedTnt || pSource instanceof MinecartTNT) && Config.CONFIG.disableTNTGriefing.get())
            override = true;

        if (override) {
            ParticleOptions particles = pRadius >= 2.0f ? pLargeExplosionParticles : pSmallExplosionParticles;

            Explosion explosion = new Explosion(
                    (Level) (Object) this,
                    pSource, pDamageSource,
                    pDamageCalculator,
                    pX, pY, pZ,
                    pRadius,
                    pFire,
                    Explosion.BlockInteraction.KEEP,
                    particles, particles,
                    pExplosionSound);
            explosion.explode();
            explosion.finalizeExplosion(true);

            cir.setReturnValue(explosion);
            cir.cancel();
        }
    }
}
