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
public class CripplingStartEvent extends Event 
{
	

	AttributeModifier crippling = new AttributeModifier(UUID.fromString("2d537d28-8d70-4456-800e-bcbc4462e5fe"),
			"crippling", (double)-0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	@SubscribeEvent
	public void onCripplingStart(PotionAddedEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.CRIPPLING.get())) 
		{
			if(entity.hasEffect(LavaEffects.CRIPPLING.get())== false)
			{
				entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(crippling);
				entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(crippling);
				entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(crippling);
			} 
		}
	}
	
	@SubscribeEvent
	public void onCripplingEnd(PotionRemoveEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.CRIPPLING.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(crippling);
			entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(crippling);
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(crippling);
		}
	}
	
	@SubscribeEvent
	public void onCripplingExpire(PotionExpiryEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.CRIPPLING.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(crippling);
			entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(crippling);
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(crippling);
		}
	}
}