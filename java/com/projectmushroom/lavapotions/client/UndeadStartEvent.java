package com.projectmushroom.lavapotions.client;

import java.util.UUID;

import com.projectmushroom.lavapotions.effect.LavaEffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class UndeadStartEvent extends Event {
	
	AttributeModifier undead = new AttributeModifier(UUID.fromString("e686fb30-8e39-42e5-989d-cacf9929aa8f"),
			"undead", 2.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	
	@SubscribeEvent
	public void onUndeadStart(PotionAddedEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.UNDEAD.get())) 
		{
			if(entity.hasEffect(LavaEffects.UNDEAD.get()) == false)
			{
				entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addTransientModifier(undead);
				if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null)
				{
					entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(undead);
				}
				entity.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(undead);
			} 
		}
	}
	
	@SubscribeEvent
	public void onUndeadEnd(PotionRemoveEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.UNDEAD.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(undead);
			if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null)
			{
				entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(undead);
			}
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(undead);
		}
	}
	
	@SubscribeEvent
	public void onUndeadExpire(PotionExpiryEvent event) 
	{
		LivingEntity entity = event.getEntityLiving();
		if(event.getPotionEffect().getEffect().equals(LavaEffects.UNDEAD.get())) 
		{
			entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(undead);
			if (entity.getAttribute(Attributes.ATTACK_DAMAGE) != null)
			{
				entity.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(undead);
			}
			entity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(undead);
		}
	}
	
	@SubscribeEvent
	public void onEntityHitEvent(LivingAttackEvent event)
	{
		if (event.getEntityLiving().hasEffect(LavaEffects.UNDEAD.get()))
		{
			DamageSource source = event.getSource();
			if(source.equals(DamageSource.LAVA) ||  source.equals(DamageSource.IN_FIRE) ||  source.equals(DamageSource.ON_FIRE))
			{
				event.getEntityLiving().heal(event.getAmount());
				event.setCanceled(true);
			}
		}
	}

}
