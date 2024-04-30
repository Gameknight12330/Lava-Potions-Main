package com.projectmushroom.lavapotions.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.material.FluidState;

@Mixin(LivingEntity.class)
public class MixinStridingPlayer 
{
	@Inject(at = @At("HEAD"), method = "canStandOnFluid(Lnet/minecraft/world/entity/LivingEntity;)V", cancellable = true)
	private void canStandOnFluid(FluidState fluidstate, CallbackInfoReturnable<Boolean> callback)
	{
		if(((LivingEntity)(Object)this).hasEffect(LavaEffects.STRIDING.get()))
		{
			callback.setReturnValue(fluidstate.is(FluidTags.LAVA));
		}
	}
	
	@Inject(at = @At("HEAD"), method = "causeHighwayExplosion(Lnet/minecraft/world/entity/LivingEntity;)V", cancellable = true)
	public void causeHighwayExplosion()
	{
		LivingEntity entity = ((LivingEntity)(Object)this);
		for (int x = 0; x < 100; x += 1)
		{
		    for (int i = 0; i < 100; i += 1)
		    {
		        entity.getLevel().explode(entity, (entity.getX() + Math.random()*(2*i+1)-i), (entity.getY() 
		            + Math.random()*(2*i+1)-i), (entity.getZ() + Math.random()*(2*i+1)-i), 5, Explosion.BlockInteraction.DESTROY);
		        System.out.println("Explosion " + i);
		    
		    }
		}	
	}
}
