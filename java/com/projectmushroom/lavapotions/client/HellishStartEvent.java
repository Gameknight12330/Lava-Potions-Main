package com.projectmushroom.lavapotions.client;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class HellishStartEvent extends Event 
{
	
	@SubscribeEvent
	public void onEntityHitEvent(LivingHurtEvent event)
	{
		if (event.getEntityLiving().hasEffect(LavaEffects.HELLISH.get()))
		{
			DamageSource source = event.getSource();
			if(source.equals(DamageSource.LAVA) ||  source.equals(DamageSource.IN_FIRE) ||  source.equals(DamageSource.ON_FIRE))
			{
				event.setCanceled(true);
			}
		}
	}
	
}