package com.projectmushroom.lavapotions.client;

import java.util.UUID;

import com.projectmushroom.lavapotions.LavaPotions;
import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LavaPotions.MOD_ID)
public class InvincStartEvent extends Event 
{
	

	AttributeModifier invinc = new AttributeModifier(UUID.fromString("fa6b2284-df85-4296-81c8-0182efe7faba"),
			"invinc", 25.0D, AttributeModifier.Operation.ADDITION);
	
	@SubscribeEvent
	public void onInvicStart(PotionAddedEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.INVINC.get())) 
		{
			if(entity.hasEffect(LavaEffects.INVINC.get())== false)
			{
				entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(invinc);
			} 
		}
	}
	
	@SubscribeEvent
	public void onInvincEnd(PotionRemoveEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.INVINC.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(invinc);
		}
	}
	
	@SubscribeEvent
	public void onInvincExpire(PotionExpiryEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.INVINC.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(invinc);
		}
	}
	
	@SubscribeEvent
	public void onEntityHitEvent(LivingHurtEvent event)
	{
		if (event.getEntityLiving().hasEffect(LavaEffects.INVINC.get()))
		{
			event.setCanceled(true);
		}
	}
	
}