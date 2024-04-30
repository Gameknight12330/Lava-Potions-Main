package com.projectmushroom.lavapotions.client;

import java.util.UUID;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class BurningSpeedStartEvent extends Event 
{
	

	AttributeModifier burningspeed = new AttributeModifier(UUID.fromString("148f2676-f904-440b-9b98-2946b8f9234b"),
			"burning_speed", 2.0D, AttributeModifier.Operation.ADDITION);
	
	@SubscribeEvent
	public void onBurningSpeedStart(PotionAddedEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.BURNING_SPEED.get()))
		{
			if(entity.hasEffect(LavaEffects.BURNING_SPEED.get())== false)
			{
				entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(burningspeed);
			}
		}
	}
	
	@SubscribeEvent
	public void onBurningSpeedEnd(PotionRemoveEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.BURNING_SPEED.get())) 
		{
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(burningspeed);
		}
	}
	
	@SubscribeEvent
	public void onBurningSpeedExpire(PotionExpiryEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.BURNING_SPEED.get())) 
		{
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(burningspeed);
		}
	}
	
}