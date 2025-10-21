package org.dyvinia.explosionrebalance.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
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
    private void expl(@Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pX, double pY, double pZ, float pRadius, boolean pFire, Level.ExplosionInteraction pExplosionInteraction, boolean pSpawnParticles, ParticleOptions pSmallExplosionParticles, ParticleOptions pLargeExplosionParticles, Holder<SoundEvent> pExplosionSound, CallbackInfoReturnable<Explosion> cir) {
        if (Config.CONFIG.disableGriefing.get() && pSource instanceof Creeper creeper) {
            Explosion explosion = new Explosion((Level) (Object) this, creeper, pDamageSource, pDamageCalculator, creeper.getX(), creeper.getY(), creeper.getZ(), pRadius, false, Explosion.BlockInteraction.KEEP, ParticleTypes.EXPLOSION_EMITTER, ParticleTypes.EXPLOSION_EMITTER, pExplosionSound);
            explosion.explode();
            explosion.finalizeExplosion(true);
            cir.setReturnValue(explosion);
            cir.cancel();
        }
    }
}
