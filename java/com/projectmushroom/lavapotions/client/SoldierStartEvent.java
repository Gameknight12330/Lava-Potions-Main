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
public class SoldierStartEvent extends Event 
{
	

	AttributeModifier soldier = new AttributeModifier(UUID.fromString("73644ae6-4356-4a04-8a33-f9ef411cac07"),
			"soldier", 2.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	@SubscribeEvent
	public void onSoldierStart(PotionAddedEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.SOLDIER.get())) 
		{
			if(entity.hasEffect(LavaEffects.SOLDIER.get())== false)
			{
				entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(soldier);
				if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null)
				{
					entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(soldier);
				}
				entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(soldier);
			} 
		}
	}
	
	@SubscribeEvent
	public void onSoldierEnd(PotionRemoveEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.SOLDIER.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(soldier);
			if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null)
			{
				entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(soldier);
			}
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(soldier);
		}
	}
	
	@SubscribeEvent
	public void onSoldierExpire(PotionExpiryEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.SOLDIER.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(soldier);
			if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null)
			{
				entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(soldier);
			}
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(soldier);
		}
	}
}