package com.projectmushroom.lavapotions.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
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
}
